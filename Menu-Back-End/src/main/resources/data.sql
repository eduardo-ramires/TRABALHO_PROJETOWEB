INSERT INTO usuarios (nome, mesa, tipo, senha)
SELECT 'Admin', 0, 'ADM', 'admin123'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE tipo = 'ADM' AND nome = 'Admin');

INSERT INTO usuarios (nome, mesa, tipo, senha)
SELECT 'Mesa 1', 1, 'USUARIO', 'mesa1'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE mesa = 1);
