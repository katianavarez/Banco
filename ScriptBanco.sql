DROP DATABASE XIMBANK;
CREATE DATABASE XimBank;
USE XimBank;
CREATE TABLE Clientes (
    idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(100) NOT NULL,
    apellidoMaterno VARCHAR(100),
    domicilio VARCHAR(150) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    contraseña VARCHAR(300) NOT NULL
);

CREATE TABLE Cuentas (
    idCuenta INT PRIMARY KEY AUTO_INCREMENT,
    numCuenta BIGINT UNIQUE NOT NULL,
    fechaApertura DATE NOT NULL,
    saldo DECIMAL(12,2) NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);
ALTER TABLE Cuentas ADD COLUMN estado ENUM('Activa', 'Cancelada') DEFAULT 'Activa';

CREATE TABLE Transacciones (
    idTransaccion INT PRIMARY KEY AUTO_INCREMENT,
    monto DECIMAL(12,2) NOT NULL,
    fechaHora DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    idCuentaOrigen INT NOT NULL,
    FOREIGN KEY (idCuentaOrigen) REFERENCES Cuentas(idCuenta)
);

CREATE TABLE Transferencias (
    idTransaccion INT PRIMARY KEY,
    idCuentaDestino INT NOT NULL,
    FOREIGN KEY (idTransaccion) REFERENCES Transacciones(idTransaccion),
    FOREIGN KEY (idCuentaDestino) REFERENCES Cuentas(idCuenta)
);

CREATE TABLE Retiros (
    idTransaccion INT PRIMARY KEY,
    folio BIGINT UNIQUE NOT NULL,
    contraseña VARCHAR(300) NOT NULL,
    estado ENUM('COBRADO', 'NO_COBRADO') NOT NULL,
    FOREIGN KEY (idTransaccion) REFERENCES Transacciones(idTransaccion)
);

DELIMITER //
CREATE PROCEDURE realizarTransferencia(
    IN cuentaOrigen INT,
    IN cuentaDestino INT,
    IN monto DECIMAL(12,2)
)
BEGIN
    DECLARE saldoDisponible DECIMAL(12,2);
    START TRANSACTION;
    SELECT saldo INTO saldoDisponible FROM Cuentas WHERE idCuenta = cuentaOrigen;
    IF saldoDisponible >= monto THEN
        UPDATE Cuentas SET saldo = saldo - monto WHERE idCuenta = cuentaOrigen;
        UPDATE Cuentas SET saldo = saldo + monto WHERE idCuenta = cuentaDestino;
        INSERT INTO Transacciones (monto, idCuentaOrigen) VALUES (monto, cuentaOrigen);
        SET @idTransaccion = LAST_INSERT_ID();
        INSERT INTO Transferencias (idTransaccion, idCuentaDestino) VALUES (@idTransaccion, cuentaDestino);
        COMMIT;
    ELSE
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo insuficiente';
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE EVENT caducarRetirosNoCobrados
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
    UPDATE Retiros r
    JOIN Transacciones t ON r.idTransaccion = t.idTransaccion
    SET r.estado = 'NO_COBRADO'
    WHERE r.estado = 'COBRADO'
      AND TIMESTAMPDIFF(MINUTE, t.fechaHora, NOW()) > 10;
END //
DELIMITER ;


DELIMITER //
CREATE TRIGGER evitarTransferenciaMismaCuenta
BEFORE INSERT ON Transferencias
FOR EACH ROW
BEGIN
    DECLARE cuentaOrigen INT;
    -- Obtener la cuenta origen de la transacción
    SELECT idCuentaOrigen INTO cuentaOrigen FROM Transacciones WHERE idTransaccion = NEW.idTransaccion;
    -- Verificar si la cuenta destino es la misma que la origen
    IF cuentaOrigen = NEW.idCuentaDestino THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puedes transferir dinero a la misma cuenta';
    END IF;
END //
DELIMITER ;








