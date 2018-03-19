

INSERT INTO Person (id, version, first_name, age) VALUES (1, 0, 'Пётр', 20);

INSERT INTO Person (id, version, first_name, age) VALUES (2, 0, 'John', 25);

INSERT INTO House (id, version, address, person_id) VALUES (1, 0, 'ул.Цюрупы, 16', 1);

INSERT INTO House (id, version, address, person_id) VALUES (2, 0, 'ул.Лунина, 7', 2);

INSERT INTO Document (code, name) VALUES (3, 'Свидетельство о рождении');
INSERT INTO Document (code, name) VALUES (7, 'Военный билет');
INSERT INTO Document (code, name) VALUES (10, 'Паспорт иностранного гражданина');
INSERT INTO Document (code, name) VALUES (21, 'Паспорт гражданина Российской Федерации');

INSERT INTO Country (code, name) VALUES (643, 'Российская Федерация');
INSERT INTO Country (code, name) VALUES (688, 'Республика Сербия');
INSERT INTO Country (code, name) VALUES (724, 'Королевство Испания');
INSERT INTO Country (code, name) VALUES (8, 'Республика Албания');

INSERT INTO User (login, password, name, is_active) VALUES ('user_1', 'sha-2 hash', 'Антон', true);
INSERT INTO User (login, password, name, is_active) VALUES ('user_2', 'sha-2 hash', 'Дмитрий', true);
INSERT INTO User (login, password, name, is_active) VALUES ('user_3', 'sha-2 hash', 'Василий', false);
INSERT INTO User (login, password, name, is_active) VALUES ('user_4', 'sha-2 hash', 'Олег', true);
INSERT INTO User (login, password, name, is_active) VALUES ('user_5', 'sha-2 hash', 'Маша', false);
INSERT INTO User (login, password, name, is_active) VALUES ('user_6', 'sha-2 hash', 'Gerard', false);
INSERT INTO User (login, password, name, is_active) VALUES ('user_7', 'sha-2 hash', 'Milovan', true);
INSERT INTO User (login, password, name, is_active) VALUES ('user_8', 'sha-2 hash', 'Albanian', true);

INSERT INTO Activation (code, user_login) VALUES ('some hash code 1', 'user_3');
INSERT INTO Activation (code, user_login) VALUES ('some hash code 2', 'user_5');
INSERT INTO Activation (code, user_login) VALUES ('some hash code 3', 'user_6');

INSERT INTO Organization (name, full_name, inn, kpp, address, phone, is_active)
VALUES ('Пятерочка', 'Торговая сеть "Пятерочка"', '123456789ИНН', '123456КПП', 'Россия, Москва, ул.Гоголя 23Б',
'+78123123456', true);
INSERT INTO Organization (name, full_name, inn, kpp, address, phone, is_active)
VALUES ('Почта России', 'ФГУП "Почта России"', '123456789ИНН', '123456КПП', 'Россия, Москва, ул.Красная 1А',
'+78123175422', true);

INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Пятерочка Питер', '+78234736215', 'ул. Кронштадтская 32', true, 1);
INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Пятерочка Пенза', '+78234735678', 'ул. Московская 1', true, 1);
INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Пятерочка Воронеж', '+78234731213', 'ул. Онежская 4', false, 1);
INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Почта России Пенза', '+78234735321', 'ул. Московская 2', true, 2);
INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Почта России Москва', '+78234735678', 'ул. Герцена 20 строение 3', false, 2);
INSERT INTO Office (name, phone, address, is_active, org_id)
VALUES ('Почта России Москва', '+78234735118', 'ул. Герцена 20 строение 4', true, 2);

INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Иван', 'Иванов', null, 'Админ', '+78234731111', true, 643
);
INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Петр', 'Петров', null, 'Консультант', '+78234731111', true, 643
);
INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Сергей', 'Сергеев', null, 'Программист', '+78234731111', true, 643
);
INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Милован', 'Иванович', null, 'Курьер', '+78234731111', true, 688
);
INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Жерар', 'Пике', null, 'SEO специалист', '+78234731111', true, 724
);
INSERT INTO Employee (first_name, last_name, middle_name, position, phone,
 is_identified, citizenship_code) VALUES (
'Марьина', 'Мария', null, 'Ландшафтный дизайнер', '+78234731111', true, 643
);

INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC1234', '2020-12-10', 21, 1
);
INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC1233', '2020-11-01', 21, 2
);
INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC3233', '2020-11-11', 21, 3
);
INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC3333', '2020-11-11', 10, 4
);
INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC2233', '2020-11-11', 10, 5
);
INSERT INTO Employees_document (doc_number, doc_date, doc_code, employee_id) VALUES (
'DOC2230', '2020-11-11', 21, 6
);

INSERT INTO Office_Employee (employee_id, office_id) VALUES (1, 1);
INSERT INTO Office_Employee (employee_id, office_id) VALUES (2, 2);
INSERT INTO Office_Employee (employee_id, office_id) VALUES (3, 4);
INSERT INTO Office_Employee (employee_id, office_id) VALUES (4, 4);
INSERT INTO Office_Employee (employee_id, office_id) VALUES (5, 6);
INSERT INTO Office_Employee (employee_id, office_id) VALUES (6, 6);