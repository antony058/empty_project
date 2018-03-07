CREATE TABLE IF NOT EXISTS Person (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    age        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS House (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    address    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Person_House (
    person_id   INTEGER  NOT NULL,
    house_id    INTEGER  NOT NULL
);

CREATE INDEX IX_Person_House_Id ON Person_House (house_id);
ALTER TABLE Person_House ADD FOREIGN KEY (house_id) REFERENCES House(id);

CREATE INDEX IX_House_Person_Id ON Person_House (person_id);
ALTER TABLE Person_House ADD FOREIGN KEY (person_id) REFERENCES Person(id);

CREATE TABLE IF NOT EXISTS Doc_types (
    code INTEGER PRIMARY KEY,
    name VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS Countries (
    code INTEGER PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS User (
    login VARCHAR(25) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    name VARCHAR(25)  NOT NULL,
    isActive BOOLEAN NOT NULL
);

CREATE INDEX IX_User_Login ON User (login);

CREATE TABLE IF NOT EXISTS Activation (
    code VARCHAR(50) PRIMARY KEY,
    userLogin VARCHAR(25)
);

CREATE INDEX IX_Activation_Code ON Activation (code);

CREATE INDEX IX_Activation_UserLogin ON Activation (userLogin);
ALTER TABLE Activation ADD FOREIGN KEY (userLogin) REFERENCES User(login);

CREATE TABLE IF NOT EXISTS Organization (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    fullName VARCHAR(80) NOT NULL,
    inn VARCHAR(12) NOT NULL,
    kpp VARCHAR(9) NOT NULL,
    address VARCHAR(70) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    isActive BOOLEAN NOT NULL
);

CREATE INDEX IX_Organization_Id ON Organization (id);

CREATE INDEX IX_Organization_Name ON Organization (name);

CREATE TABLE IF NOT EXISTS Office (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    address  VARCHAR(70) NOT NULL,
    isActive BOOLEAN NOT NULL,
    orgId INTEGER
);

CREATE INDEX IX_Office_Id ON Office (id);

CREATE INDEX IX_Office_OrgId ON Office (orgId);
ALTER TABLE Office ADD FOREIGN KEY (orgId) REFERENCES Organization(id);

CREATE TABLE IF NOT EXISTS Employee (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    middleName VARCHAR(50),
    position VARCHAR(40) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    docNumber VARCHAR(20) NOT NULL,
    docDate DATE NOT NULL,
    isIdentified BOOLEAN NOT NULL,
    docCode INTEGER,
    citizenshipCode INTEGER,
    officeId INTEGER
);

CREATE INDEX IX_Employee_Id ON Employee (id);

CREATE INDEX IX_Employee_OfficeId ON Employee (officeId);
ALTER TABLE Employee ADD FOREIGN KEY (officeId) REFERENCES Office(id);

CREATE INDEX IX_Employee_DocCode ON Employee (docCode);
ALTER TABLE Employee ADD FOREIGN KEY (docCode) REFERENCES Doc_types(code);

CREATE INDEX IX_Employee_CitizenshipCode ON Employee (citizenshipCode);
ALTER TABLE Employee ADD FOREIGN KEY (citizenshipCode) REFERENCES Countries(code);
