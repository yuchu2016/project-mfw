package com.yuchu.projectmfw.service;

import com.yuchu.projectmfw.common.SmsResult;
import com.yuchu.projectmfw.domain.CodeInfo;
import com.yuchu.projectmfw.domain.Order;
import com.yuchu.projectmfw.domain.Passenger;
import com.yuchu.projectmfw.domain.sms.SmsRequest;
import com.yuchu.projectmfw.domain.sms.SmsResponse;
import com.yuchu.projectmfw.feign.SmsClient;
import com.yuchu.projectmfw.mq.sender.OrderSender;
import com.yuchu.projectmfw.repository.CodeInfoRepository;
import com.yuchu.projectmfw.repository.OrderRepository;
import com.yuchu.projectmfw.repository.PassengerRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 10:02
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private CodeInfoRepository codeInfoRepository;
    @Autowired
    private OrderSender orderSender;
    @Autowired
    private SmsClient smsClient;

    public Order saveOrder(Order order) {
        CodeInfo departCityInfo = codeInfoRepository.findFirstByCityNameLike(order.getDepartCity());
        order.setDepartCityCode(departCityInfo.getThreeCode());
        CodeInfo desCityInfo = codeInfoRepository.findFirstByCityNameLike(order.getDesCity());
        order.setDesCityCode(desCityInfo.getThreeCode());
        order = orderRepository.save(order);
        log.info("保存成功!订单编号为:{}", order.getId());
        orderSender.send(order.getId());
        return order;
    }

    public Order getOrder(String id) {
        return orderRepository.findOne(id);
    }


    public void executeOrder(String id) throws Exception {
        WebDriver webDriver = init();
        log.info("浏览器初始化完成...");
        Order order = orderRepository.findOne(id);
        List<Passenger> passengers = passengerRepository.findByOrderId(order.getId());
        log.info("开始登录...");
        webDriver.get("http://passport.mafengwo.cn/");
//        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        WebElement passport = webDriver.findElement(By.name("passport"));
//        passport.sendKeys("18245480832");
        passport.sendKeys(order.getAccount());
        WebElement password = webDriver.findElement(By.name("password"));
//        password.sendKeys("huangqing07");
        password.sendKeys(order.getPassword());
        WebElement submit = webDriver.findElement(By.xpath("//*[@id=\"_j_login_form\"]/div[2]/button[1]"));
//        Thread.sleep(5000);
        submit.click();
        try {
            WebElement loginCheck = webDriver.findElement(By.xpath("//*[@id=\"_j_login_form\"]/ul/li[3]/input"));
            loginCheck.click();
            log.warn("请10s内输入验证码!");
//            Scanner scanner = new Scanner(System.in);
//            String keys = scanner.nextLine();
            Thread.sleep(10000);
//            WebElement inputCheck = webDriver.findElement(By.xpath("//*[@id=\"_j_login_form\"]/ul/li[3]/input"));
//            inputCheck.sendKeys(keys);
            WebElement submitCheck = webDriver.findElement(By.xpath("//*[@id=\"_j_login_form\"]/div[2]/button[1]"));
            submitCheck.click();
        } catch (Exception e) {
            log.info("登录无验证码");
        }
        log.info("登录完成");
//        Thread.sleep(5000);
        //登录结束
        //选择出发地  目的地
        log.info("开始线路选择...");
        webDriver.get("https://m.mafengwo.cn/flight/list?departCity="
                + order.getDepartCity() +
                "&departCode="
                + order.getDepartCityCode() +
                "&destCity="
                + order.getDesCity() +
                "&destCode="
                + order.getDesCityCode() +
                "&type=oneWay&departDate="
                + order.getDate() +
                "&destDate=&seat_go_selected=");
        Thread.sleep(6000);
        //选择出发地  目的地 结束

        // 选择航班号
        List<WebElement> flightList = webDriver.findElements(By.cssSelector("a[class='item ng-scope']"));
        for (WebElement fight : flightList) {
            WebElement webElement = fight.findElement(By.cssSelector("div[class='airline-info ng-binding']"));
            String flightName = webElement.getText();
            String pattern = "[a-zA-Z]{2}[0-9]{4}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(flightName);
            if (m.find()) {
                String flightNo = m.group(0);
                //todo  航班号
                if (flightNo.equals(order.getFlightNo())) {
//                    fight.click();
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", fight);
                    log.info("线路选择完成");
                    break;
                }
            }
        }
        // 选择航班号结束
        //WebElement orderButton = webDriver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[1]"));

        //orderButton.click();
        //获取舱级
        log.info("开始舱级选择...");
        List<WebElement> typeList = webDriver.findElements(By.cssSelector("div[class='item ng-scope']"));
        for (WebElement type : typeList) {
            WebElement levelElement = type.findElement(By.cssSelector("div:nth-child(3) > span"));
            if (levelElement.getText().contains(order.getLevel())) {
                WebElement orderButton = type.findElement(By.cssSelector("div:nth-child(1)"));
                orderButton.click();
                log.info("舱级选择完成");
                break;
            } else {
                //todo
                log.error("未找到舱级{}...", order.getLevel());
            }
        }
        // 添加乘客
        //张三
        //
        //身份证:310108196009081238
        //
        //删除
        //张三
        log.info("开始添加乘客...");
        WebElement addPerson = webDriver.findElement(By.xpath("//*[@id=\"psger_view\"]/a"));
        addPerson.click();
        for (Passenger passenger : passengers) {
            //*[@id="showAddPsgerPage"]/div/div[1]/a
            WebElement addPersonButton = webDriver.findElement(By.xpath("//*[@id=\"showAddPsgerPage\"]/div/div[1]/a"));
            addPersonButton.click();
            Thread.sleep(5000);
            WebElement name = webDriver.findElement(By.xpath("//*[@id=\"addPsgerBtn\"]/div/div[1]/ul/li[1]/input"));
            // todo 姓名
            name.sendKeys(passenger.getName());
            WebElement idNum = webDriver.findElement(By.xpath("//*[@id=\"addPsgerBtn\"]/div/div[1]/ul/li[3]/input"));
            Thread.sleep(3000);
            // todo  身份证号码
            idNum.sendKeys(passenger.getIdNum());
            WebElement submitPerson = webDriver.findElement(By.xpath("//*[@id=\"addPsgerBtn\"]/div/div[2]/a"));
            submitPerson.click();
            log.info("添加乘客<{}>成功", passenger.getName());
            Thread.sleep(3000);
        }
        log.info("添加乘客完成");
        // 添加乘客结束
        Thread.sleep(6000);
        log.info("开始选择乘客...");
        List<WebElement> personList = webDriver.findElements(By.cssSelector("div[ng-repeat='one in psgerList']"));
        for (WebElement person : personList) {
            String personName = person.findElement(By.cssSelector("p[class='tit ng-binding']")).getText();
            String personId = person.findElement(By.cssSelector("p[class='ng-binding']")).getText().toUpperCase();
            String idPattern = "[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
            Pattern r = Pattern.compile(idPattern);
            Matcher m = r.matcher(personId);
            if (m.find()) {
                personId = m.group(0);
            }
            //todo
            for (Passenger passenger : passengers) {
                if (personName.equals(passenger.getName()) && personId.equals(passenger.getIdNum().toUpperCase())) {
                    log.info("乘客<{}>已选择", passenger.getName());
                    person.click();
                }
            }

        }
        WebElement submitOne = webDriver.findElement(By.xpath("//*[@id=\"showAddPsgerPage\"]/div/div[2]/a"));
        submitOne.click();
        log.info("选择乘客完成");
        //添加乘客结束
        //添加联系人
        log.info("开始添加联系人...");
        WebElement buyerNameInput = webDriver.findElement(By.xpath("//*[@id=\"contacts_view\"]/ul/li[1]/input"));
        // todo 联系人姓名
        buyerNameInput.clear();
        buyerNameInput.sendKeys(order.getContactName());
        WebElement buyerPhoneInput = webDriver.findElement(By.xpath("//*[@id=\"contacts_view\"]/ul/li[2]/input"));
        buyerPhoneInput.clear();
        // todo 联系人姓名手机号
        buyerPhoneInput.sendKeys(order.getContactPhone());
        // todo 联系人邮箱
        WebElement emailInput = webDriver.findElement(By.xpath("//*[@id=\"contacts_view\"]/ul/li[3]/input"));
        emailInput.clear();
        emailInput.sendKeys(order.getContactEmail());
        //todo 验证码  有的不需要
        try {
            WebElement checkButton = webDriver.findElement(By.xpath("//*[@id=\"contacts_view\"]/ul/li[3]/div"));
//            checkButton.click();
            log.warn("需要输入验证码...");
            SmsRequest smsRequest= new SmsRequest.SmsRequestBuilder().
                    ownNumber(order.getContactPhone())
                    .sendNumber("1069115392306518")
                    .build();
            SmsResult<SmsResponse> smsResult= smsClient.getCode(smsRequest);
            log.info(smsResult.toString());
            String code = smsResult.getData().getCONTENT();
            log.info("接收到验证码为:{}",code);
            WebElement checkInput = webDriver.findElement(By.xpath("//*[@id=\"contacts_view\"]/ul/li[3]/input"));
            checkInput.sendKeys(code);
        } catch (Exception e) {
            log.info("下单无验证码");
        }
        log.info("添加联系人完成");

        //结束添加联系人
        //提交订单
        log.info("即将提交订单...");
        WebElement submitOrder = webDriver.findElement(By.xpath("//*[@id=\"container\"]/div[8]/div[1]/a"));
        Thread.sleep(2000);
        //submitOrder.click();
        //提交订单结束
        Thread.sleep(4000);
        log.info("提交订单完成,窗口即将关闭...");
        webDriver.close();
        //支付
       /* WebElement alipayChose = webDriver.findElement(By.xpath("/html/body/div[2]/div[3]/div[3]"));
        alipayChose.click();
        WebElement paySubmit = webDriver.findElement(By.xpath("//*[@id=\"_j_pay_submit\"]"));
        paySubmit.click();*/
        //结束支付
    }

    private WebDriver init() {
        System.getProperties().setProperty("webdriver.chrome.driver", "D:/temp/chrome/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //设置user agent为Android
        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Mobile Safari/537.36");
        //实例化chrome对象，并加入选项

        //设置代理
//        String sslProxyIpAndPort = "184.29.47.90:3128";
//        String httpProxyIpAndPort = "119.28.152.208:80";
        String sslProxyIpAndPort = "localhost:1080";
        String httpProxyIpAndPort = "localhost:1080";
        DesiredCapabilities cap = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        proxy
                .setHttpProxy(httpProxyIpAndPort)
//                .setFtpProxy(proxyIpAndPort)
                .setSslProxy(sslProxyIpAndPort);
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        cap.setCapability(CapabilityType.PROXY, proxy);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver webDriver = new ChromeDriver(cap);
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return webDriver;
    }
}
