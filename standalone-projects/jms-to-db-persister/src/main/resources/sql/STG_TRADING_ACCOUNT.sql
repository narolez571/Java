create table STG_TRADING_ACCOUNT
(
  TRADING_ACC_NK       VARCHAR2(255),
  TRADING_ACC_DOMAIN   VARCHAR2(255),
  TRADING_ACC_VERSION  NUMBER(20),
  TRADING_ACC_ID       NUMBER(10),
  STATUS_UPDT_DATE     DATE,
  STATUS               VARCHAR2(255),
  OPEN_DATE            DATE,
  CLIENT_CLASS         VARCHAR2(255),
  RISK_LOCATION        VARCHAR2(255),
  RISK_CITY_CODE       NUMBER(20),
  SIMPLE_NAME          VARCHAR2(255),
  SHORT_NAME           VARCHAR2(255),
  COMMUNICATION_TYPE   VARCHAR2(255),
  ADDRESS_LINE         VARCHAR2(255),
  CITY                 VARCHAR2(255),
  COUNTRY              VARCHAR2(255),
  FUNCTION_REFERENCE   VARCHAR2(255),
  ADDRESS_LINE2        VARCHAR2(255),
  ADDRESS_LINE3        VARCHAR2(255),
  ADDRESS_LINE4        VARCHAR2(255),
  STG_EFFECTIVE_DATE   DATE,
  STG_LOAD_DATE        DATE,
  STG_LOAD_ID          NUMBER(20),
  STG_JOB_RUN_ID       NUMBER(20)	
);