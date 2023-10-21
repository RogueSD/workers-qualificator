INSERT INTO service.specialization(
    "name"
) VALUES
    ('Cборщик'),
    ('Установщик'),
    ('Ремонтник');

INSERT INTO service.qualification(
    "specialization_id",
    "name",
    "minimal_manufactured_products",
    "maximal_defective_products_percentage"
) VALUES
        (1, 'Сборщик 1 разряда', 0, 1.0),
        (1, 'Сборщик 2 разряда', 100, 0.5),
        (1, 'Сборщик 3 разряда', 1000, 0.25),
        (1, 'Сборщик 4 разряда', 2000, 0.125),
        (2, 'Установщик 1 разряда', 0, 1.0),
        (2, 'Установщик 2 разряда', 100, 0.5),
        (2, 'Установщик 3 разряда', 1000, 0.25),
        (2, 'Установщик 4 разряда', 2000, 0.125),
        (3, 'Ремонтник 1 разряда', 0, 1.0),
        (3, 'Ремонтник 2 разряда', 100, 0.5),
        (3, 'Ремонтник 3 разряда', 1000, 0.25),
        (3, 'Ремонтник 4 разряда', 2000, 0.125);

INSERT INTO service.worker (
    "name",
    "surname",
    "qualification_id",
    "audit_results",
    "manufactured_products_count",
    "defective_products_count"
) VALUES
    ('Иван', 'Иванов', 1, 'Новенький', 0, 0),
    ('Олег', 'Васильев', 8, 'Опытный мастер, который успешно работает 10 лет', 10000, 356),
    ('Семён', 'Требушев', 10, 'Начаниющий ремонтник', 115, 20);

INSERT INTO service.complaint (
    "worker_id",
    "content"
) VALUES
    (2, 'Опоздал на работу'),
    (2, 'Сломал рукоятку машины');