#!/bin/bash

# Script per avviare l'ambiente locale

# Carica le variabili d'ambiente locali
set -a
source .env.local
set +a

# Ferma eventuali container esistenti con lo stesso nome
docker-compose -f docker-compose.local.yml down

# Avvia i servizi
docker-compose -f docker-compose.local.yml up 

# Mostra lo stato dei container
#docker-compose -f docker-compose.local.yml ps

#echo "Servizi avviati in ambiente locale"
#echo "Neo4j UI: http://localhost:${NEO4J_HTTP_PORT}"
#echo "API: http://localhost:${APP_PORT}"