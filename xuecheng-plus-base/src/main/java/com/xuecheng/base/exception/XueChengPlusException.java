package com.xuecheng.base.exception;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public class XueChengPlusException extends RuntimeException {
   String errCode;
   private String errMessage;
   public XueChengPlusException() {
      super();
   }
   public XueChengPlusException(String errMessage) {
      super(errMessage);
      this.errMessage = errMessage;
   }

   public XueChengPlusException(String errCode, String errMessage) {
      this.errCode = errCode;
      this.errMessage = errMessage;
   }
   public String getErrMessage() {
      return errMessage;
   }
   public String getErrCode() {
      return errCode;
   }
   public static void cast(CommonError commonError){
      throw new XueChengPlusException(commonError.getErrMessage());
   }
   public static void cast(String errMessage){
      throw new XueChengPlusException(errMessage);
   }

}
