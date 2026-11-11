db = db.getSiblingDB("validacao_certificados");

db.createCollection("certificados", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "emissorId",
        "nomeAluno",
        "curso",
        "cargaHoraria",
        "hash",
        "status"
      ],
      properties: {
        _id: {
          bsonType: "int"
        },
        emissorId: {
          bsonType: "int"
        },
        nomeAluno: {
          bsonType: "string"
        },
        curso: {
          bsonType: "string"
        },
        cargaHoraria: {
          bsonType: "int"
        },
        dataConclusao: {
          bsonType: "date"
        },
        hash: {
          bsonType: "string"
        },
        status: {
          enum: ["VALIDO", "INVALIDO"]
        },
        dataEmissao: {
          bsonType: "date"
        }
      }
    }
  }
});

db.certificados.createIndex(
  { hash: 1 },
  { unique: true }
);

db.createCollection("emissores", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "nome",
        "cnpjCpf",
        "email",
        "senha",
        "status",
        "perfil"
      ],
      properties: {
        _id: {
          bsonType: "int"
        },
        nome: {
          bsonType: "string"
        },
        cnpjCpf: {
          bsonType: "string"
        },
        email: {
          bsonType: "string"
        },
        senha: {
          bsonType: "string"
        },
        status: {
          enum: ["PENDENTE", "ATIVO", "BLOQUEADO"]
        },
        perfil: {
          bsonType: "string"
        }
      }
    }
  }
});

db.certificados.createIndex({ emissorId: 1 });
