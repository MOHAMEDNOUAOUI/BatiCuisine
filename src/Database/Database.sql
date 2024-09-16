CREATE TYPE Etat_Projet AS ENUM ('ENCOURS' , 'TERMINE' , 'ANNULE')
CREATE TYPE ClientsStatut AS ENUM ('ACTIVE' , 'DELETED')


CREATE TABLE Projets (
    id_Projets SERIAL PRIMARY KEY,
    projects_name VARCHAR(100) NOT NULL,
    marge_benificiare DECIMAL(10, 2),
    cout_total DECIMAL(12, 2),
    etat_projet Etat_Projet,
    clients_id_reference int,
    foreign key (clients_id_reference) references Clients(id_Clients)
);



CREATE TABLE Clients (
    id_Clients SERIAL primary key,
    nom VARCHAR(50),
    adresse VARCHAR(80),
    telephone VARCHAR(20),
    estProfessionel boolean,
    statut_user ClientsStatut
)

CREATE TABLE Composants (
    id_Composants SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    tauxTVA DECIMAL(5, 2),
    typeComposant VARCHAR(50),
    projet_id INT REFERENCES Projets(id_Projets)
);

CREATE TABLE MainDœuvre (
    tauxHoraire DECIMAL(10, 2),
    heuresTravai DECIMAL(10, 2),
    productiviteOuvrier DECIMAL(10, 2)
) INHERITS (Composants);

CREATE TABLE Matériaux (
    coutUnitaire DECIMAL(10, 2),
    quantity DECIMAL(10, 2),
    coutTransport DECIMAL(10, 2),
    coefficientQualite DECIMAL(10, 2)
) INHERITS (Composants);

CREATE TABLE  Devis (
    id_Devis SERIAL primary key,
    montantEstime DECIMAL(10, 2),
    dateEmission DATE,
    dateValidite DATE,
    accepte BOOLEAN,
    projet_id INT REFERENCES Projets(id_Projets)
)
