package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author MrMa
 * @version 1.0
 * @description   和前端约定返回的异常信息模型
 */
public class RestErrorResponse implements Serializable {

   private String errMessage;

   public RestErrorResponse(String errMessage){
      this.errMessage= errMessage;
   }

   public String getErrMessage() {
      return errMessage;
   }

   public void setErrMessage(String errMessage) {
      this.errMessage = errMessage;
   }
}
