INSERT INTO tb_products (sku, name, description, price, quantity_in_stock, min_stock, category, status)
SELECT 'TECH-001', 'Monitor Dell 27 4K UHD', 'Monitor IPS com hub USB-C e ajuste de altura', 2499.90, 15, 5, 'HARDWARE', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM tb_products WHERE sku = 'TECH-001');

INSERT INTO tb_products (sku, name, description, price, quantity_in_stock, min_stock, category, status)
SELECT 'TECH-002', 'Teclado Mecânico Keychron K2', 'Teclado sem fio 75% Switch Gateron Brown', 680.00, 8, 3, 'PERIFERICOS', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM tb_products WHERE sku = 'TECH-002');

INSERT INTO tb_products (sku, name, description, price, quantity_in_stock, min_stock, category, status)
SELECT 'OFF-101', 'Cadeira Ergonômica Mesh Pro', 'Cadeira executiva com apoio lombar ajustável', 1250.00, 3, 5, 'ESCRITORIO', 'LOW_STOCK'
WHERE NOT EXISTS (SELECT 1 FROM tb_products WHERE sku = 'OFF-101');

INSERT INTO tb_products (sku, name, description, price, quantity_in_stock, min_stock, category, status)
SELECT 'NET-505', 'Roteador Wi-Fi 6 Gigabit Dual Band', 'Roteador mesh AX3000 de alta performance', 450.00, 22, 5, 'REDES', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM tb_products WHERE sku = 'NET-505');
