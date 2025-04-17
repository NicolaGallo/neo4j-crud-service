#!/bin/bash

# Script per avviare l'ambiente di produzione che si connette a Neo4j Aura

# Chiedi conferma prima di procedere
read -p "Stai per avviare l'ambiente di PRODUZIONE. Confermi? (y/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    echo "Operazione annullata"
    exit 1
fi

# Carica le variabili d'ambiente di produzione
set -a
source .env.prod
set +a

# Controlla le variabili obbligatorie
if [ -z "$NEO4J_URI" ]; then
    echo "Errore: NEO4J_URI non impostato nell'ambiente. Verifica la configurazione in .env.prod"
    exit 1
fi

if [ -z "$NEO4J_USERNAME" ] || [ -z "$NEO4J_PASSWORD" ]; then
    echo "Errore: Credenziali Neo4j non impostate nell'ambiente. Verifica la configurazione in .env.prod"
    exit 1
fi

if [ -z "$DOCKER_REGISTRY" ]; then
    echo "Errore: DOCKER_REGISTRY non impostato nell'ambiente"
    exit 1
fi

if [ -z "$VERSION" ]; then
    echo "Attenzione: VERSION non impostata, verrà utilizzato 'latest'"
    export VERSION=latest
fi

# Verifica la connessione a Neo4j Aura (optional)
echo "Verificando la connessione a Neo4j Aura..."
if command -v nc &> /dev/null; then
    URI_HOST=$(echo $NEO4J_URI | sed -E 's/^neo4j(\+s)?:\/\///' | sed -E 's/:.*//')
    if nc -z -w 5 $URI_HOST 443 &> /dev/null; then
        echo "Connessione a Neo4j Aura verificata con successo"
    else
        echo "Attenzione: Impossibile verificare la connessione a Neo4j Aura. Assicurati che le credenziali siano corrette."
    fi
else
    echo "Il comando 'nc' non è disponibile. Impossibile verificare la connessione a Neo4j Aura."
fi

# Ferma eventuali container esistenti con lo stesso nome
docker-compose -f docker-compose.prod.yml down

# Avvia il servizio
docker-compose -f docker-compose.prod.yml up -d

# Mostra lo stato dei container
docker-compose -f docker-compose.prod.yml ps

echo "Servizio avviato in ambiente di PRODUZIONE"
echo "API: http://localhost:${APP_PORT}"