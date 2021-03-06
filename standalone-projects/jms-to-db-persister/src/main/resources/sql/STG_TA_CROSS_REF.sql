create table STG_TA_CROSS_REF
(
  TRADING_ACC_NK             VARCHAR2(255),
  TRADING_ACC_DOMAIN         VARCHAR2(255),
  TRADING_ACC_VERSION        NUMBER(20),
  TRADING_ACC_ID             NUMBER(10),
  ALT_IDENTIFIER_ID          VARCHAR2(255),
  ALT_IDENTIFIER_DOMAIN      VARCHAR2(255),
  ALT_DOMAIN_CODE            VARCHAR2(255),
  ALT_IDENTIFIER_STATUS_DATE DATE,
  ALT_IDENTIFIER_STATUS      VARCHAR2(50),
  ALT_IDENTIFIER_BLOCKED     VARCHAR2(20),
  STG_EFFECTIVE_DATE         DATE,
  STG_LOAD_DATE              DATE,
  STG_LOAD_ID                NUMBER(20),
  STG_JOB_RUN_ID             NUMBER(20)
);
