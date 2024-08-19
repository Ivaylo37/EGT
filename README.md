# Currency Data Service

This service fetches and caches currency exchange rate data from Fixer.io.

## Overview

- Data Source: http://data.fixer.io/api/
- Update Frequency: Hourly
- Caching: Redis (containerized with Docker)
- Database: PostgreSQL (local)
- API Documentation: Swagger
- Test Coverage: --% on services

## Technical Stack

- Fixer.io API for real-time currency data
- Redis for caching frequent requests
- Docker for containerization
- PostgreSQL as the primary database
- Swagger for API documentation and testing