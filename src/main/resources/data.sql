INSERT INTO House (id, version, address) VALUES (1, 0, 'ул.Цюрупы, 16');

INSERT INTO House (id, version, address) VALUES (2, 0, 'ул.Лунина, 7');

INSERT INTO Person (id, version, first_name, age) VALUES (1, 0, 'Пётр', 20);

INSERT INTO Person (id, version, first_name, age) VALUES (2, 0, 'John', 25);

INSERT INTO Person_House (person_id, house_id) VALUES (1, 1);

INSERT INTO Person_House (person_id, house_id) VALUES (1, 2);

INSERT INTO Doc_types (code, name) VALUES (3, 'Свидетельство о рождении');
INSERT INTO Doc_types (code, name) VALUES (7, 'Военный билет');
INSERT INTO Doc_types (code, name) VALUES (10, 'Паспорт иностранного гражданина');
INSERT INTO Doc_types (code, name) VALUES (21, 'Паспорт гражданина Российской Федерации');

INSERT INTO Countries (code, name) VALUES (643, 'Российская Федерация');
INSERT INTO Countries (code, name) VALUES (688, 'Республика Сербия');
INSERT INTO Countries (code, name) VALUES (724, 'Королевство Испания');
INSERT INTO Countries (code, name) VALUES (8, 'Республика Албания');

INSERT INTO User (login, password, name, isActive) VALUES ('user_1', 'sha-2 hash', 'Антон', true);
INSERT INTO User (login, password, name, isActive) VALUES ('user_2', 'sha-2 hash', 'Дмитрий', true);
INSERT INTO User (login, password, name, isActive) VALUES ('user_3', 'sha-2 hash', 'Василий', false);
INSERT INTO User (login, password, name, isActive) VALUES ('user_4', 'sha-2 hash', 'Олег', true);
INSERT INTO User (login, password, name, isActive) VALUES ('user_5', 'sha-2 hash', 'Маша', false);
INSERT INTO User (login, password, name, isActive) VALUES ('user_6', 'sha-2 hash', 'Gerard', false);
INSERT INTO User (login, password, name, isActive) VALUES ('user_7', 'sha-2 hash', 'Milovan', true);
INSERT INTO User (login, password, name, isActive) VALUES ('user_8', 'sha-2 hash', 'Albanian', true);

INSERT INTO Activation (code, userLogin) VALUES ('some hash code_1', 'user_3');
INSERT INTO Activation (code, userLogin) VALUES ('some hash code_2', 'user_5');
INSERT INTO Activation (code, userLogin) VALUES ('some hash code_3', 'user_6');

INSERT INTO Organization (name, fullName, inn, kpp, address, phone, isActive)
VALUES ('Пятерочка', 'Торговая сеть \"Пятерочка\"', '123456789ИНН', '123456КПП', 'Россия, Москва, ул.Гоголя 23Б',
'+78123123456', true);
INSERT INTO Organization (name, fullName, inn, kpp, address, phone, isActive)
VALUES ('Почта России', 'ФГУП \"Почта России\"', '123456789ИНН', '123456КПП', 'Россия, Москва, ул.Красная 1А',
'+78123175422', true);

INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Пятерочка Питер', '+78234736215', 'ул. Кронштадтская 32', true, 1);
INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Пятерочка Пенза', '+78234735678', 'ул. Московская 1', true, 1);
INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Пятерочка Воронеж', '+78234731213', 'ул. Онежская 4', false, 1);
INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Почта России Пенза', '+78234735321', 'ул. Московская 2', true, 2);
INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Почта России Москва', '+78234735678', 'ул. Герцена 20 строение 3', false, 2);
INSERT INTO Office (name, phone, address, isActive, orgId)
VALUES ('Почта России Москва', '+78234735118', 'ул. Герцена 20 строение 4', true, 2);

INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Иван', 'Иванов', null, 'Админ', '+78234731111', 'DOC1234', '2020-12-10',
 true, 21, 643, 1
);
INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Петр', 'Петров', null, 'Консультант', '+78234731111', 'DOC1233', '2020-11-01',
 true, 21, 643, 2
);
INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Сергей', 'Сергеев', null, 'Программист', '+78234731111', 'DOC3233', '2020-11-11',
 true, 21, 643, 4
);
INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Милован', 'Иванович', null, 'Курьер', '+78234731111', 'DOC3333', '2020-11-11',
 true, 10, 688, 4
);
INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Жерар', 'Пике', null, 'SEO специалист', '+78234731111', 'DOC2233', '2020-11-11',
 true, 10, 724, 6
);
INSERT INTO Employee (firstName, lastName, middleName, position, phone, docNumber, docDate,
 isIdentified, docCode, citizenshipCode, officeId) VALUES (
'Марьина', 'Мария', null, 'Ландшафтный дизайнер', '+78234731111', 'DOC2233', '2020-11-11',
 true, 21, 643, 6
);