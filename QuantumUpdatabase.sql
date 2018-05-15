
CREATE TABLE STATE_MAHCINE
(
  state_id VARCHAR2(20 CHAR),
  state_description VARCHAR2(20 CHAR) NOT NULL,
  CONSTRAINT state_info_pk PRIMARY KEY(state_id)
);

CREATE TABLE NODE_INFO
(
  node_id VARCHAR2(20 CHAR),
  admin_id VARCHAR2(20 CHAR),
  node_creation_date DATE NOT NULL,
  node_status VARCHAR2(20 CHAR) NOT NULL REFERENCES STATE_MAHCINE(state_id),
  CONSTRAINT node_info_pk PRIMARY KEY(node_id)
);

CREATE TABLE NODE_CALENDER
(
  calender_id VARCHAR2(20 CHAR),	
  node_active_start_time VARCHAR2(20 CHAR) NOT NULL,
  node_active_end_time VARCHAR2(20 CHAR) NOT NULL,
  node_id_fk VARCHAR2(20 CHAR) NOT NULL REFERENCES NODE_INFO(node_id),
  CONSTRAINT node_calender_pk PRIMARY KEY(calender_id)
);

CREATE TABLE NODE_TASKS
(
  task_id VARCHAR2(20 CHAR),
  start_time DATE NOT NULL,
  end_time DATE,
  node_id_fk VARCHAR2(20 CHAR) NOT NULL REFERENCES NODE_INFO(node_id),
  CONSTRAINT task_table_pk PRIMARY KEY(task_id)
);