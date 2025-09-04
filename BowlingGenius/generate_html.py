html_parts = [
    """<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bowling Genius</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .game-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-top: 30px;
        }
        .frame {
            border: 2px solid #dee2e6;
            border-radius: 5px;
            padding: 10px;
            margin: 5px;
            text-align: center;
            background-color: #fff;
            transition: all 0.3s;
        }
        .frame.active {
            border-color: #0d6efd;
            background-color: #e7f1ff;
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(13, 110, 253, 0.2);
        }
        .frame-number {
            font-weight: bold;
            border-bottom: 1px solid #dee2e6;
            margin-bottom: 5px;
            padding-bottom: 3px;
        }
        .rolls {
            min-height: 40px;
            display: flex;
            justify-content: space-between;
            margin-bottom: 5px;
        }
        .roll {
            flex: 1;
            border: 1px solid #dee2e6;
            margin: 0 1px;
            padding: 2px;
            min-width: 25px;
        }""",
    
    """        .score {
            font-weight: bold;
            min-height: 30px;
            padding-top: 5px;
            border-top: 1px solid #dee2e6;
        }
        .pins-buttons .btn {
            min-width: 40px;
            margin: 2px;
        }
        .tenth-frame .rolls {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 2px;
        }
        .tenth-frame .roll {
            min-width: 20px;
        }
    </style>
</head>""",
    
    """<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="game-container">
                    <h1 class="text-center mb-4">Bowling Genius</h1>
                    
                    <!-- Formulaire de démarrage d'une nouvelle partie -->
                    <div th:if="${game == null || game.id == null}" class="text-center">
                        <h3>Commencer une nouvelle partie</h3>
                        <form th:action="@{/bowling/new}" method="post" class="row g-3 justify-content-center">
                            <div class="col-auto">
                                <input type="text" name="playerName" class="form-control" placeholder="Votre nom" required>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-primary">Nouvelle Partie</button>
                            </div>
                        </form>
                    </div>""",
    
    """                    
                    <!-- Affichage du jeu -->
                    <div th:if="${game != null && game.id != null}">
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <h3>Joueur: <span th:text="${game.playerName}"></span></h3>
                            </div>
                            <div class="col-md-6 text-end">
                                <form th:action="@{/bowling/" + ${game.id} + "/reset}" method="post" class="d-inline">
                                    <button type="submit" class="btn btn-danger">Réinitialiser</button>
                                </form>
                                <a href="/bowling" class="btn btn-secondary">Nouveau Joueur</a>
                            </div>
                        </div>""",
    
    """                        
                        <!-- Affichage des frames -->
                        <div class="row mb-4">
                            <div th:each="frame : ${game.frames}" 
                                 th:class="${frame.frameNumber == 10} ? 'col-12 col-md-4 tenth-frame' : 'col-6 col-md-3 col-lg-2'">
                                <div th:class="${frame.frameNumber == game.currentFrame ? 'frame active' : 'frame'}">
                                    <div class="frame-number" th:text="'Frame ' + ${frame.frameNumber}"></div>
                                    <div class="rolls">
                                        <!-- Affichage des lancers -->
                                        <div th:if="${frame.frameNumber < 10}">
                                            <div class="roll" th:if="${frame.firstRoll != null}" 
                                                 th:text="${frame.strike ? 'X' : frame.firstRoll}"></div>
                                            <div class="roll" th:if="${frame.firstRoll == null}"></div>
                                            
                                            <div class="roll" th:if="${frame.secondRoll != null}" 
                                                 th:text="${frame.spare ? '/' : (frame.strike ? '' : frame.secondRoll)}"></div>
                                            <div class="roll" th:if="${frame.secondRoll == null && frame.firstRoll != null && !frame.strike}"></div>
                                            <div class="roll" th:if="${frame.firstRoll == null}"></div>
                                        </div>""",
    
    """                                        
                                        <!-- Cas spécial pour la 10ème frame -->
                                        <div th:if="${frame.frameNumber == 10}" class="tenth-frame">
                                            <div class="roll" th:if="${frame.firstRoll != null}" 
                                                 th:text="${frame.firstRoll == 10 ? 'X' : frame.firstRoll}"></div>
                                            <div class="roll" th:if="${frame.firstRoll == null}"></div>
                                            
                                            <div class="roll" th:if="${frame.secondRoll != null}" 
                                                 th:text="${frame.secondRoll == 10 ? 'X' : (frame.firstRoll + frame.secondRoll == 10 && frame.firstRoll != 10 ? '/' : frame.secondRoll)}"></div>
                                            <div class="roll" th:if="${frame.secondRoll == null && frame.firstRoll != null}"></div>
                                            
                                            <div class="roll" th:if="${frame.thirdRoll != null}" 
                                                 th:text="${frame.thirdRoll == 10 ? 'X' : (frame.secondRoll + frame.thirdRoll == 10 && frame.secondRoll != 10 ? '/' : frame.thirdRoll)}"></div>
                                            <div class="roll" th:if="${frame.thirdRoll == null && (frame.firstRoll == 10 || (frame.firstRoll + frame.secondRoll == 10))}"></div>
                                        </div>
                                    </div>
                                    <div class="score" th:if="${frame.frameScore > 0}" th:text="${frame.frameScore}"></div>
                                </div>
                            </div>
                        </div>""",
    
    """                        
                        <!-- Boutons de lancer -->
                        <div th:if="${!game.complete}" class="text-center mt-4">
                            <h4>Lancer la boule</h4>
                            <div class="pins-buttons">
                                <form th:action="@{/bowling/game/" + ${game.id} + "/roll" method="post" class="d-inline">
                                    <input type="hidden" name="pins" value="0">
                                    <button type="submit" class="btn btn-outline-primary">0</button>
                                </form>
                                <form th:each="i : ${#numbers.sequence(1, 10)}" th:action="@{/bowling/game/" + ${game.id} + "/roll}" method="post" class="d-inline">
                                    <input type="hidden" name="pins" th:value="${i}">
                                    <button type="submit" class="btn btn-outline-primary" th:text="${i}"></button>
                                </form>
                            </div>
                        </div>
                        
                        <!-- Message de fin de partie -->
                        <div th:if="${game.complete}" class="alert alert-success text-center mt-4">
                            <h4>Partie terminée !</h4>
                            <p>Score final: <strong th:text="${game.score}"></strong></p>
                            <a href="/bowling" class="btn btn-primary">Nouvelle Partie</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>"""
]

# Écrire le fichier HTML complet
with open('src/main/resources/templates/bowling.html', 'w', encoding='utf-8') as f:
    f.write(''.join(html_parts))
