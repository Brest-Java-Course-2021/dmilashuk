ALTER TABLE EMPLOYEE DROP CONSTRAINT FK_EMPLOYEE;

TRUNCATE TABLE EMPLOYEE;
TRUNCATE TABLE DEPARTMENT;

ALTER TABLE EMPLOYEE ADD CONSTRAINT FK_EMPLOYEE FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID);

ALTER TABLE DEPARTMENT ALTER COLUMN DEPARTMENT_ID RESTART WITH 1;
ALTER TABLE EMPLOYEE ALTER COLUMN EMPLOYEE_ID RESTART WITH 1;
