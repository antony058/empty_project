CREATE TABLE IF NOT EXISTS Person (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    age        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS House (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    address    VARCHAR(50) NOT NULL,
    person_id INTEGER
);

ALTER TABLE House ADD FOREIGN KEY (person_id) REFERENCES Person(id);

CREATE TABLE IF NOT EXISTS Document (
    code INTEGER PRIMARY KEY,
    name VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS Country (
    code INTEGER PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS User (
    login VARCHAR(25) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    name VARCHAR(25)  NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE INDEX IX_User_Login ON User (login);

CREATE TABLE IF NOT EXISTS Activation (
    code VARCHAR(50) PRIMARY KEY,
    user_login VARCHAR(25)
);

CREATE INDEX IX_Activation_Code ON Activation (code);

CREATE INDEX IX_Activation_UserLogin ON Activation (user_login);
ALTER TABLE Activation ADD FOREIGN KEY (user_login) REFERENCES User(login);

CREATE TABLE IF NOT EXISTS Organization (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    full_name VARCHAR(80),
    inn VARCHAR(12) NOT NULL,
    kpp VARCHAR(9),
    address VARCHAR(70),
    phone VARCHAR(12),
    version INTEGER DEFAULT 0,
    is_active BOOLEAN NOT NULL
);

CREATE INDEX IX_Organization_Id ON Organization (id);

CREATE INDEX IX_Organization_Name ON Organization (name);

CREATE TABLE IF NOT EXISTS Office (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(12),
    address  VARCHAR(70),
    is_active BOOLEAN,
    version INTEGER DEFAULT 0,
    org_id INTEGER
);

CREATE INDEX IX_Office_Id ON Office (id);

CREATE INDEX IX_Office_OrgId ON Office (org_id);
ALTER TABLE Office ADD FOREIGN KEY (org_id) REFERENCES Organization(id);

CREATE TABLE IF NOT EXISTS Employee (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    position VARCHAR(40) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    is_identified BOOLEAN NOT NULL,
    version INTEGER DEFAULT 0,
    citizenship_code INTEGER
);

CREATE INDEX IX_Employee_Id ON Employee (id);

CREATE INDEX IX_Employee_CitizenshipCode ON Employee (citizenship_code);
ALTER TABLE Employee ADD FOREIGN KEY (citizenship_code) REFERENCES Country(code);

CREATE TABLE IF NOT EXISTS Office_Employee (
    office_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL
);

CREATE INDEX IX_Employee_Office_Id ON Office_Employee (office_id);
ALTER TABLE Office_Employee ADD FOREIGN KEY (office_id) REFERENCES Office(id);

CREATE INDEX IX_Office_Employee_Id ON Office_Employee (employee_id);
ALTER TABLE Office_Employee ADD FOREIGN KEY (employee_id) REFERENCES Employee(id);

CREATE TABLE IF NOT EXISTS Employees_document (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    doc_number VARCHAR(20) NOT NULL,
    doc_date DATE NOT NULL,
    version INTEGER DEFAULT 0,
    doc_code INTEGER NOT NULL,
    employee_id INTEGER NOT NULL
);

CREATE INDEX IX_Employees_Document_DocCode ON Employees_document (doc_code);
ALTER TABLE Employees_document ADD FOREIGN KEY (doc_code) REFERENCES Document(code);

CREATE INDEX IX_Employees_Document_EmployeeId ON Employees_document (employee_id);
ALTER TABLE Employees_document ADD FOREIGN KEY (employee_id) REFERENCES Employee(id);