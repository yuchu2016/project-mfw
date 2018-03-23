package com.yuchu.projectmfw.repository;

import com.yuchu.projectmfw.domain.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 10:01
 */
public interface CodeInfoRepository extends JpaRepository<CodeInfo,Integer>{
    CodeInfo findFirstByCityNameLike(String cityName);
}
