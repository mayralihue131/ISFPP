CREATE TABLE mew_usuarios (
    email VARCHAR(320) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(40) NOT NULL,
    rol_activo VARCHAR(20) NOT NULL
        CHECK (rol_activo IN ('CLIENTE', 'CONDUCTOR'))
);

CREATE TABLE mew_vehiculos (
    patente VARCHAR(20) PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    capacidad_pasajeros INTEGER NOT NULL CHECK (capacidad_pasajeros > 0),
    tipo_vehiculo VARCHAR(20) NOT NULL,
    categoria_vehiculo VARCHAR(20) NOT NULL,
    ubicacion_latitud DOUBLE PRECISION,
    ubicacion_longitud DOUBLE PRECISION
);

CREATE TABLE mew_vehiculo_servicios (
    patente VARCHAR(20) NOT NULL REFERENCES mew_vehiculos(patente)
        ON DELETE CASCADE,
    tipo_servicio VARCHAR(20) NOT NULL,
    PRIMARY KEY (patente, tipo_servicio)
);

CREATE TABLE mew_conductores (
    email VARCHAR(320) PRIMARY KEY REFERENCES mew_usuarios(email)
        ON DELETE CASCADE,
    licencia VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    categoria_activa VARCHAR(20) NOT NULL
);

CREATE TABLE mew_conductor_vehiculos (
    conductor_email VARCHAR(320) NOT NULL REFERENCES mew_conductores(email)
        ON DELETE CASCADE,
    patente VARCHAR(20) NOT NULL REFERENCES mew_vehiculos(patente),
    vehiculo_activo BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (conductor_email, patente)
);

CREATE TABLE mew_servicios (
    nombre VARCHAR(100) PRIMARY KEY,
    tarifa_base NUMERIC(12, 2) NOT NULL,
    precio_km NUMERIC(12, 2) NOT NULL,
    precio_minuto NUMERIC(12, 2) NOT NULL,
    tipo_vehiculo VARCHAR(20) NOT NULL,
    categoria_vehiculo VARCHAR(20) NOT NULL,
    tipo_servicio VARCHAR(20) NOT NULL
);

CREATE TABLE mew_viajes (
    id UUID PRIMARY KEY,
    cliente_email VARCHAR(320) NOT NULL REFERENCES mew_usuarios(email),
    conductor_email VARCHAR(320) REFERENCES mew_usuarios(email),
    vehiculo_patente VARCHAR(20) REFERENCES mew_vehiculos(patente),
    servicio_nombre VARCHAR(100) REFERENCES mew_servicios(nombre),
    origen_latitud DOUBLE PRECISION NOT NULL,
    origen_longitud DOUBLE PRECISION NOT NULL,
    destino_latitud DOUBLE PRECISION NOT NULL,
    destino_longitud DOUBLE PRECISION NOT NULL,
    motivo_cancelacion TEXT,
    rol_cancela VARCHAR(20)
        CHECK (rol_cancela IN ('CLIENTE', 'CONDUCTOR'))
);

CREATE TABLE mew_viaje_estados (
    viaje_id UUID NOT NULL REFERENCES mew_viajes(id) ON DELETE CASCADE,
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (viaje_id, fecha_hora, estado)
);
