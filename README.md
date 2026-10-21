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

Every command is on a single line so it can be pasted directly. Pick the section that matches your terminal, because quoting rules differ.

### Windows cmd

```bat
curl http://localhost:8080/api/clubs

curl http://localhost:8080/api/clubs/1

curl -X POST http://localhost:8080/api/clubs -H "Content-Type: application/json" -d "{\"name\": \"Club de cinema\", \"description\": \"Projections et discussions.\"}"

curl -X PUT http://localhost:8080/api/clubs/1 -H "Content-Type: application/json" -d "{\"name\": \"Club d'echecs avance\", \"description\": \"Tournois et analyses de parties.\"}"

curl -X DELETE http://localhost:8080/api/clubs/1

curl http://localhost:8080/api/utilisateurs

curl http://localhost:8080/api/utilisateurs/1

curl -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d "{\"nom\": \"charlie\", \"password\": \"charlie123\"}"

curl -X PUT http://localhost:8080/api/utilisateurs/2 -H "Content-Type: application/json" -d "{\"nom\": \"alice2\"}"

curl -X DELETE http://localhost:8080/api/utilisateurs/3
```

Error cases:

```bat
curl -i http://localhost:8080/api/clubs/999

curl -i -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d "{\"nom\": \"admin\", \"password\": \"whatever\"}"
```

### Windows PowerShell

Use `curl.exe`, not `curl` (in PowerShell, `curl` is an alias for `Invoke-WebRequest`).

```powershell
curl.exe http://localhost:8080/api/clubs

curl.exe http://localhost:8080/api/clubs/1

curl.exe -X POST http://localhost:8080/api/clubs -H "Content-Type: application/json" -d '{\"name\": \"Club de cinema\", \"description\": \"Projections et discussions.\"}'

curl.exe -X PUT http://localhost:8080/api/clubs/1 -H "Content-Type: application/json" -d '{\"name\": \"Club echecs avance\", \"description\": \"Tournois.\"}'

curl.exe -X DELETE http://localhost:8080/api/clubs/1

curl.exe http://localhost:8080/api/utilisateurs

curl.exe http://localhost:8080/api/utilisateurs/1

curl.exe -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d '{\"nom\": \"charlie\", \"password\": \"charlie123\"}'

curl.exe -X PUT http://localhost:8080/api/utilisateurs/2 -H "Content-Type: application/json" -d '{\"nom\": \"alice2\"}'

curl.exe -X DELETE http://localhost:8080/api/utilisateurs/3
```

Error cases:

```powershell
curl.exe -i http://localhost:8080/api/clubs/999

curl.exe -i -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d '{\"nom\": \"admin\", \"password\": \"whatever\"}'
```

### Git Bash / Linux / macOS

```bash
curl http://localhost:8080/api/clubs

curl http://localhost:8080/api/clubs/1

curl -X POST http://localhost:8080/api/clubs -H "Content-Type: application/json" -d '{"name": "Club de cinéma", "description": "Projections et discussions."}'

curl -X PUT http://localhost:8080/api/clubs/1 -H "Content-Type: application/json" -d '{"name": "Club d'\''échecs avancé", "description": "Tournois et analyses de parties."}'

curl -X DELETE http://localhost:8080/api/clubs/1

curl http://localhost:8080/api/utilisateurs

curl http://localhost:8080/api/utilisateurs/1

curl -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d '{"nom": "charlie", "password": "charlie123"}'

curl -X PUT http://localhost:8080/api/utilisateurs/2 -H "Content-Type: application/json" -d '{"nom": "alice2"}'

curl -X DELETE http://localhost:8080/api/utilisateurs/3
```

Error cases:

```bash
curl -i http://localhost:8080/api/clubs/999

curl -i -X POST http://localhost:8080/api/utilisateurs -H "Content-Type: application/json" -d '{"nom": "admin", "password": "whatever"}'
```

### Tips

- **Expected status codes:** 201 on create, 204 on delete, 404 for a missing id, 409 for a duplicate username.
- **`-i`** prints the status line and headers, useful for checking those codes.
- **Pretty output:** pipe through `jq`, e.g. `curl http://localhost:8080/api/clubs | jq` (bash/PowerShell).
- **IDs:** after deletes, some ids may no longer exist. Run the list commands first to see which are available.
- **Accents in cmd/PowerShell:** the examples avoid them because those terminals can mangle non-ASCII characters in request bodies. To use them in `cmd`, run `chcp 65001` first.
- **`400 Bad Request`:** usually means the JSON body was broken by quoting (wrong section for your terminal) or the `-d` part was lost when the command was split across lines.