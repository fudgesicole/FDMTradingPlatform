--Drop objects in use below
DROP TABLE tp_users CASCADE CONSTRAINTS;
DROP TABLE tp_role CASCADE CONSTRAINTS;
DROP TABLE tp_user_role CASCADE CONSTRAINTS;
DROP TABLE tp_company CASCADE CONSTRAINTS;
DROP TABLE tp_request CASCADE CONSTRAINTS;
DROP TABLE tp_trade CASCADE CONSTRAINTS;
DROP TABLE tp_shares_authorized CASCADE CONSTRAINTS;

DROP SEQUENCE tp_request_id_seq;
DROP SEQUENCE tp_trade_id_seq;

DROP VIEW tp_buyers;
DROP VIEW tp_sellers;
DROP VIEW tp_current_holdings;
DROP VIEW tp_current_shareholder_shares;
DROP VIEW tp_current_stock_stats;

CREATE TABLE tp_users (
  user_id NUMBER(10) NOT NULL,
  pw VARCHAR2(100) NOT NULL,
  user_name      VARCHAR2(50) NOT NULL,
  first_name     VARCHAR2(50) NOT NULL,
  last_name      VARCHAR2(50) NOT NULL,
  CONSTRAINT tp_users_pk PRIMARY KEY (user_id),
  CONSTRAINT tp_users_user_name_uk        UNIQUE (user_name)
);

CREATE TABLE tp_role(
  role_id NUMBER(6) NOT NULL,
  role_name VARCHAR2(25),
  CONSTRAINT tp_role_id_pk PRIMARY KEY(role_id),
  CONSTRAINT tp_role_name_uk UNIQUE(role_name)
);

CREATE TABLE tp_user_role(
  user_id NUMBER(10) NOT NULL,
  role_id NUMBER(6) NOT NULL,
  CONSTRAINT ur_user_id_fk FOREIGN KEY (user_id) REFERENCES tp_users(user_id),
  CONSTRAINT ur_role_id_fk FOREIGN KEY (role_id) REFERENCES tp_role(role_id)
);

CREATE TABLE tp_company (
  company_id NUMBER(15) NOT NULL,
  company_name VARCHAR2(50) NOT NULL,
  stock_id NUMBER(6) NOT NULL,
  starting_price NUMBER(10,4),
  CONSTRAINT tp_company_id_pk PRIMARY KEY(company_id), 
  CONSTRAINT tp_company_name_id_uk UNIQUE(company_name),  
  CONSTRAINT tp_company_stock_id_uk UNIQUE(stock_id)
);

CREATE TABLE tp_request(
  request_id NUMBER(5) NOT NULL,
  parent_request_id NUMBER(5) NULL,
  shares_filled NUMBER(7) NULL,
  shareholder_id NUMBER(10) NOT NULL,
  request_date DATE NOT NULL,
  buy_sell VARCHAR2(4 BYTE) NOT NULL,
  status VARCHAR2(20 BYTE) NOT NULL,
  stock_id NUMBER(6) NOT NULL,
  shares NUMBER(7) NOT NULL,
  minimum_shares NUMBER(7) NOT NULL,
  time_in_force VARCHAR2(20 BYTE) NOT NULL,
  limit_price NUMBER(10,4),
  stop_price NUMBER(10,4),
  CONSTRAINT tp_request_id_pk PRIMARY KEY(request_id),
  CONSTRAINT tp_request_parent_id_fk FOREIGN KEY(parent_request_id) REFERENCES tp_request(request_id),
  CONSTRAINT tp_request_shareholder_id_fk FOREIGN KEY (shareholder_id) REFERENCES tp_users(user_id),
  CONSTRAINT tp_request_stock_id_fk FOREIGN KEY (stock_id) REFERENCES tp_company(company_id),
  CONSTRAINT tp_request_buy_sell_chk CHECK (buy_sell IN ('BUY','SELL')),
  CONSTRAINT tp_request_status_chk CHECK (status IN ('ACTIVE','PARTIAL FILL','COMPLETED','CANCELLED')),
  CONSTRAINT tp_request_tif_chk CHECK (time_in_force IN ('DAY ONLY','GOOD UNTIL CANCELLED','IMMEDIATE OR CANCEL'))
);
    
CREATE TABLE tp_trade (
  trade_id NUMBER(9) NOT NULL,
  stock_id NUMBER(6) NOT NULL,
  transaction_time DATE NOT NULL,
  shares NUMBER(12,4) NOT NULL,
  share_price NUMBER(10,4),
  price_total NUMBER(20,2),
  buyer_id NUMBER(10) NOT NULL,
  seller_id NUMBER(10) NOT NULL,
  buy_request_id NUMBER(5) NOT NULL,
  sell_request_id NUMBER(5) NOT NULL,
  CONSTRAINT tp_trade_id_pk PRIMARY KEY (trade_id),
  CONSTRAINT tp_trade_stock_id_fk FOREIGN KEY (stock_id) REFERENCES tp_company(company_id),
  CONSTRAINT tp_trade_buyer_id_fk FOREIGN KEY (buyer_id) REFERENCES tp_users(user_id),
  CONSTRAINT tp_trade_seller_id_fk FOREIGN KEY (seller_id) REFERENCES tp_users(user_id),
  CONSTRAINT tp_trade_buy_request_id_fk FOREIGN KEY (buy_request_id) REFERENCES tp_request(request_id),
  CONSTRAINT tp_trade_sell_request_id_fk FOREIGN KEY (sell_request_id) REFERENCES tp_request(request_id)
);

CREATE TABLE tp_shares_authorized (
  stock_id NUMBER(6) NOT NULL,
  time_start DATE NOT NULL,
  time_end DATE NULL,
  authorized NUMBER(12) NOT NULL,
  CONSTRAINT tp_shares_authorized_pk PRIMARY KEY (stock_id, time_start),
  CONSTRAINT tp_shares_auth_stock_id_fk FOREIGN KEY (stock_id) REFERENCES tp_company(stock_id)
);

CREATE SEQUENCE tp_request_id_seq
   INCREMENT BY 1
   START WITH 1;

CREATE SEQUENCE tp_trade_id_seq
   INCREMENT BY 1
   START WITH 1;

CREATE OR REPLACE VIEW tp_buyers
 AS
  SELECT t_buy.buyer_id AS buyer_id,  
  t_buy.stock_id AS stock_id,
  sum(t_buy.shares) AS shares
  FROM tp_trade t_buy
  WHERE t_buy.buyer_id IS NOT NULL
  GROUP BY t_buy.buyer_id, t_buy.stock_id;
  
CREATE OR REPLACE VIEW tp_sellers
AS
SELECT t_sell.seller_id AS seller_id,  
  t_sell.stock_id AS stock_id,
  sum(t_sell.shares) AS shares
  FROM tp_trade t_sell
  WHERE t_sell.seller_id IS NOT NULL
  GROUP BY t_sell.seller_id, t_sell.stock_id;

CREATE OR REPLACE VIEW tp_current_holdings
AS 
SELECT buy.buyer_id, buy.stock_id, buy.shares-nvl(sell.shares,0) AS holdings
FROM tp_buyers buy
FULL OUTER JOIN tp_sellers sell
ON buy.buyer_id=sell.seller_id 
AND buy.stock_id=sell.stock_id
OR( buy.buyer_id IS NOT NULL
  AND buy.stock_id=sell.stock_id)
OR( sell.seller_id IS NOT NULL
  AND buy.stock_id=sell.stock_id);
  
CREATE OR REPLACE VIEW tp_current_shareholder_shares
AS
SELECT
nvl(buy.buyer_id, sell.seller_id) AS shareholder_id,
   nvl(buy.stock_id, sell.stock_id) AS  stock_id, 
   CASE nvl(buy.buyer_id, sell.seller_id)
      WHEN c.company_id THEN NULL
      ELSE nvl(buy.shares,0) - nvl(sell.shares,0)
   END AS shares
   FROM tp_buyers buy
FULL OUTER JOIN tp_sellers sell
ON buy.buyer_id=sell.seller_id 
AND buy.stock_id=sell.stock_id
JOIN tp_users sh
    ON sh.user_id = nvl(buy.buyer_id, sell.seller_id)
  JOIN tp_company c
    ON c.stock_id = nvl(buy.stock_id, sell.stock_id)
WHERE nvl(buy.shares,0) - nvl(sell.shares,0) != 0;

CREATE OR REPLACE VIEW tp_current_stock_stats
AS
SELECT
  co.stock_id,
  si.authorized current_authorized,
  sum(CASE WHEN t.seller_id = co.company_id THEN t.shares END)
    -nvl(sum(CASE WHEN t.buyer_id = co.company_id 
             THEN t.shares END),0) AS total_outstanding
FROM tp_company co
  INNER JOIN tp_shares_authorized si
     ON si.stock_id = co.stock_id
    AND si.time_end IS NULL
  LEFT OUTER JOIN tp_trade t
      ON t.stock_id = co.stock_id
GROUP BY co.stock_id, si.authorized;

COMMIT;