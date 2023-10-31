import random
import json

def generate_random_recipe(user_id, recipe_number):

    nome = f"Receita {recipe_number}"
    foto = "url_da_imagem"
    nivel_experiencia = random.choice(["FACIL", "INTERMEDIARIO", "AVANCADO"])
    visibilidade = "PUBLICA"
    cores = random.choice([True, False])

    receitaUtilizaMaterialIds = [random.randint(1, 500) for _ in range(random.randint(3, 7))]
    receitaSeparadaCategoriaIds = [random.randint(1, 50) for _ in range(random.randint(1, 4))]

    etapas = []
    num_etapas = random.randint(1, 5)
    for i in range(num_etapas):
        descricao_etapas = f"Etapa {i+1}"
        passosList = [{"descricao": f"Passo {j+1}"} for j in range(random.randint(1, 5))]
        etapa = {"descricao_etapas": descricao_etapas, "passosList": passosList}
        etapas.append(etapa)
    
    recipe = {
        "receita": {
            "nome": nome,
            "foto": foto,
            "nivel_experiencia": nivel_experiencia,
            "visibilidade": visibilidade,
            "cores": cores
        },
        "userId": user_id,
        "receitaUtilizaMaterialIds": receitaUtilizaMaterialIds,
        "receitaSeparadaCategoriaIds": receitaSeparadaCategoriaIds,
        "etapas": etapas
    }
    
    return recipe

recipes = []
recipe_counter = 1
for user_id in range(1, 101):
    for _ in range(10):
        recipe = generate_random_recipe(user_id, recipe_counter)
        recipes.append(recipe)
        recipe_counter += 1

with open("receitas.json", "w") as file:
    json.dump(recipes, file, indent=4)

print("Receitas geradas e salvas em 'receitas.json'.")