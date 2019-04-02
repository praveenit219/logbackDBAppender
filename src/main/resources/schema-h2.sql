CREATE TABLE IF NOT EXISTS pheonix_audit
(
 submissionDate DATETIME NOT NULL,
  requestid VARCHAR(255) NOT NULL,
  eID VARCHAR(50)  NOT NULL,
  status VARCHAR(50)  NOT NULL,
  service VARCHAR(50)  NOT NULL,
  authContext VARCHAR(255) ,
  ipaddresss VARCHAR(255) ,
);
