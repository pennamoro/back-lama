import random
import json

def generate_users(user_id):

    nome = f"Usuario {user_id}"
    apelido =  f"usuario{user_id}"
    email = f"usuario{user_id}@teste.dev"
    senha =  "12345678"
    
    usuario = {
        "nome": nome,
        "apelido": apelido,
        "email": email,
        "senha": senha,
        "nivel_experiencia": "INICIANTE"
    }
    
    return usuario

users = []
for user_id in range(2, 101):
        user = generate_users(user_id)
        users.append(user)

with open("usuarios.json", "w") as file:
    json.dump(users, file, indent=4)

print("Usuarios gerados e salvos em 'usuarios.json'.")