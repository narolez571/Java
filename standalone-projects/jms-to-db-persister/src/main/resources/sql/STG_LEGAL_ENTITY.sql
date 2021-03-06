CREATE TABLE STG_LEGAL_ENTITY
(
  LE_DOMAIN                VARCHAR2(255),
  LE_VERSION               NUMBER(20),
  LE_ID                    NUMBER(20),
  LEGAL_NAME               VARCHAR2(255),
  COUNTRY_DOMICILE         VARCHAR2(255),
  COUNTRY_INCORPORATION    VARCHAR2(255),
  INDUSTRY_CLASS_CODE      VARCHAR2(30),
  INDUSTRY_CLASS_TYPE      VARCHAR2(255),
  LE_STATUS_UPDT_DATE      DATE,
  LE_STATUS                VARCHAR2(255),
  CLIENT_CLASS             VARCHAR2(255),
  HASCOLLATERAL            VARCHAR2(255),
  COUNTRY_OF_RISK          VARCHAR2(255),
  IS_CONFIDENTIAL          VARCHAR2(255),
  INTL_RATING_CODE         VARCHAR2(255),
  INTL_RATING_REVIEW_DATE  DATE,
  PORTFOLIO_SEGMENT        VARCHAR2(255),
  RATING_APPROACH          VARCHAR2(255),
  BOE_CODE                 VARCHAR2(255),
  BOE_TYPE                 VARCHAR2(255),
  CA_CODE                  VARCHAR2(255),
  CA_TYPE                  VARCHAR2(255),
  CT_CODE                  VARCHAR2(255),
  CT_TYPE                  VARCHAR2(255),
  LEGACY_ID                VARCHAR2(255),
  STG_EFFECTIVE_DATE       DATE,
  STG_LOAD_DATE            DATE,
  STG_LOAD_ID              NUMBER(20),
  STG_JOB_RUN_ID           NUMBER(20)  
);
