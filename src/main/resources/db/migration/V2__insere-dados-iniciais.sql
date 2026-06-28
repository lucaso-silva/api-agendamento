INSERT INTO pacientes(nome, telefone, id_carteirinha)
VALUES('João da Silva', '8133001122', '12345678910'),
      ('Maria Oliveira', '8133003344', NULL);

INSERT INTO profissionais (nome, telefone, tipo)
VALUES('Dr. Pedro Correia', '8133112233', 'MEDICO'),
      ('Dra. Ana Souza', '8133223344', 'FISIOTERAPEUTA');

INSERT INTO agendamentos (paciente_id, profissional_id, data_agendamento, tipo_atendimento, status, motivo_cancelamento)
VALUES(1, 1, '2026-07-10 09:00:00', 'INICIAL', 'AGENDADO', NULL),
      (2, 2, '2026-07-11 14:30:00', 'RETORNO', 'CANCELADO', 'Paciente solicitou cancelamento');