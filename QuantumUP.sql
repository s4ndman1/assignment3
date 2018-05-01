CREATE SEQUENCE node_id_seq
MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE TABLE NODE_INFO
(
  node_id NUMBER DEFAULT node_id_seq.NEXTVAL,
  node_creation_date DATE NOT NULL,
  node_status VARCHAR2(20 CHAR) NOT NULL,
  CONSTRAINT node_info_pk PRIMARY KEY(node_id)
);

CREATE SEQUENCE calender_id_seq
MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;
  
CREATE TABLE NODE_CALENDER
(
  calender_id NUMBER DEFAULT calender_id_seq.NEXTVAL,
  node_active_start_time TIMESTAMP NOT NULL,
  node_active_end_time TIMESTAMP NOT NULL,
  nodecalender_id_fk NUMBER NOT NULL REFERENCES NODE_INFO(node_id),
  CONSTRAINT node_calender_pk PRIMARY KEY(calender_id)
);

CREATE SEQUENCE task_id_seq
MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;
  
CREATE TABLE TASK_TABLE
(
  task_id NUMBER DEFAULT task_id_seq.NEXTVAL,
  metric_type VARCHAR2(50 CHAR) NOT NULL,
  metric_value VARCHAR2(50 CHAR) NOT NULL,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  tasknode_fk NUMBER NOT NULL REFERENCES NODE_INFO(node_id),
  CONSTRAINT task_table_pk PRIMARY KEY(task_id)
);

