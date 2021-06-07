insert into operation_type (id, description) VALUES (1, 'COMPRA A VISTA'), (2, 'COMPRA PARCELADA'), (3, 'SAQUE'), (4, 'PAGAMENTO');

insert into account (id, document_number, avaliable_credit_limit) VALUES (1, '12345678900', 100);

insert into transaction (id, account_id, operation_type_id, amount, event_date) VALUES (1, 1, 1, -50.0, '2020-01-01T10:32:07.7199222');
insert into transaction (id, account_id, operation_type_id, amount, event_date) VALUES (2, 1, 1, -23.5, '2020-01-01T10:48:12.2135875');
insert into transaction (id, account_id, operation_type_id, amount, event_date) VALUES (3, 1, 1, -18.7, '2020-01-02T19:01:23.1458543');
insert into transaction (id, account_id, operation_type_id, amount, event_date) VALUES (4, 1, 4, 60.0, '2020-01-05T09:34:18.5893223');