# Clubs API

REST API built with Spring Boot to manage clubs and users (`Utilisateur`).

## Run

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080` (change the port in the commands below if yours differs).

## Seed data

On startup, `DataSeeder` inserts sample data if the tables are empty:

- **5 clubs** (chess, robotics, theatre, photography, reading)
- **3 users**: `admin`, `alice`, `bob`

> Passwords are stored in plain text for development only. Add a `PasswordEncoder` (e.g. `BCryptPasswordEncoder`) before using this in production.

## Endpoints

### Clubs

| Method | URL                | Description    | Success |
|--------|--------------------|----------------|---------|
| GET    | `/api/clubs`       | List all clubs | 200     |
| GET    | `/api/clubs/{id}`  | Get one club   | 200     |
| POST   | `/api/clubs`       | Create a club  | 201     |
| PUT    | `/api/clubs/{id}`  | Update a club  | 200     |
| DELETE | `/api/clubs/{id}`  | Delete a club  | 204     |

### Utilisateurs

| Method | URL                       | Description     | Success |
|--------|---------------------------|-----------------|---------|
| GET    | `/api/utilisateurs`       | List all users  | 200     |
| GET    | `/api/utilisateurs/{id}`  | Get one user    | 200     |
| POST   | `/api/utilisateurs`       | Create a user   | 201     |
| PUT    | `/api/utilisateurs/{id}`  | Update a user   | 200     |
| DELETE | `/api/utilisateurs/{id}`  | Delete a user   | 204     |

User responses never include the password (`UtilisateurResponseDTO` only exposes `id` and `nom`).

## Testing with curl

### Clubs

```bash
# List all clubs
curl http://localhost:8080/api/clubs

# Get one club
curl http://localhost:8080/api/clubs/1

# Create a club (expect 201)
curl -X POST http://localhost:8080/api/clubs \
  -H "Content-Type: application/json" \
  -d '{"name": "Club de cinéma", "description": "Projections et discussions."}'

# Update a club
curl -X PUT http://localhost:8080/api/clubs/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Club d'\''échecs avancé", "description": "Tournois et analyses de parties."}'

# Delete a club (expect 204)
curl -X DELETE http://localhost:8080/api/clubs/1
```

### Utilisateurs

```bash
# List all users (no password in the response)
curl http://localhost:8080/api/utilisateurs

# Get one user
curl http://localhost:8080/api/utilisateurs/1

# Create a user (expect 201)
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{"nom": "charlie", "password": "charlie123"}'

# Update a user (rename only, password stays unchanged)
curl -X PUT http://localhost:8080/api/utilisateurs/2 \
  -H "Content-Type: application/json" \
  -d '{"nom": "alice2"}'

# Delete a user (expect 204)
curl -X DELETE http://localhost:8080/api/utilisateurs/3
```

### Error cases

```bash
# 404: club that doesn't exist
curl -i http://localhost:8080/api/clubs/999

# 409: duplicate username (admin is already seeded)
curl -i -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{"nom": "admin", "password": "whatever"}'
```

### Tips

- **`-i`** prints the status line and headers, useful for checking 201, 204, 404 and 409.
- **Pretty output:** pipe through `jq`, e.g. `curl http://localhost:8080/api/clubs | jq`.
- **IDs:** after deletes, some ids may no longer exist. Run the list commands first to see which are available.
- **Windows cmd:** replace the `\` line continuations with `^`, and escape inner quotes: `-d "{\"name\": \"Club de cinéma\", \"description\": \"Projections.\"}"`.
- **PowerShell:** use `curl.exe` instead of `curl`, since `curl` is an alias for `Invoke-WebRequest`.