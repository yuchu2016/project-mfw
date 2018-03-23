package com.yuchu.projectmfw.common;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 9:41
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3863559687276427567L;

    @JsonView(GeneralViews.ErrorView.class)
    @Enumerated(EnumType.STRING)
    private Status status;//状态：=> SUCCESS or ERROR

    @JsonView(GeneralViews.ErrorView.class)
    private String msg;//提示信息

    @JsonView(GeneralViews.NormalView.class)
    private T content;//SUCCESS状态返回内容部分

    {
        status = Status.SUCCESS;
    }

    public static <T> Result<T> newInstance() {
        return new Result<>();
    }

    public Result<T> orGetErrorMsg(String message) {
        return this.status.equals(Status.ERROR) ? ResultGenerator.error(message) : this;
    }

    public Result<T> orGetSuccessMsg(String message) {
        return this.status.equals(Status.SUCCESS) ? ResultGenerator.ok(message, this.getContent()) : this;
    }

    public Status getStatus() {
        return status;
    }

    public Result setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getContent() {
        return content;
    }

    public Result setContent(T content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", content=" + content +
                '}';
    }

    enum Status {
        SUCCESS, ERROR
    }


}
