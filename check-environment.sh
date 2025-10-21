#!/usr/bin/env bash
set -e

echo "🔍 Verificando entorno RetailX..."

# Colores para output
GREEN="\033[0;32m"
RED="\033[0;31m"
YELLOW="\033[1;33m"
NC="\033[0m"

check_cmd() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo -e "${RED}❌ No se encontró $1${NC}"
    return 1
  else
    echo -e "${GREEN}✅ $1 encontrado${NC}"
    return 0
  fi
}

echo
echo "➡️  Verificando herramientas base..."
check_cmd java
check_cmd mvn
check_cmd docker

echo
if java -version 2>&1 | grep -q "17"; then
  echo -e "${GREEN}✔ Java 17 detectado${NC}"
else
  echo -e "${RED}⚠ No estás usando Java 17${NC}"
  echo "  ➜ Usa OpenJDK 17, Temurin 17 o GraalVM 17"
fi

echo
echo "➡️  Verificando versión de Maven..."
mvn -v | grep "Apache Maven" || echo -e "${RED}⚠ Maven no encontrado correctamente${NC}"

echo
echo "➡️  Verificando GraalVM / Native Image..."
if java -version 2>&1 | grep -qi "GraalVM"; then
  echo -e "${GREEN}✔ GraalVM detectado${NC}"
  if command -v native-image >/dev/null 2>&1; then
    echo -e "${GREEN}✔ native-image instalado${NC}"
  else
    echo -e "${YELLOW}⚠ Falta native-image. Instálalo con: gu install native-image${NC}"
  fi
else
  echo -e "${YELLOW}⚠ No estás en GraalVM. No es obligatorio si usas los Dockerfile nativos.${NC}"
fi

echo
echo "➡️  Verificando Docker..."
if docker info >/dev/null 2>&1; then
  echo -e "${GREEN}✔ Docker está en ejecución${NC}"
else
  echo -e "${RED}❌ Docker no está corriendo${NC}"
  echo "  ➜ Inicia Docker Desktop o el servicio docker antes de continuar"
fi

echo
echo "✅ Verificación completada."
echo "---------------------------------------------"
echo " Recomendado para modo nativo:"
echo "   • Java 17 (GraalVM o Mandrel)"
echo "   • Maven 3.9+"
echo "   • Docker activo"
echo "---------------------------------------------"
echo
