package com.yuchu.projectmfw.common;

public class GeneralViews {
    /** 
     * use in error views 
     */
    public interface ErrorView {}
  
    /** 
     * use in success views 
     */
    public interface NormalView extends ErrorView {}
}  