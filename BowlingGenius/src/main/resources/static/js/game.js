// Fonction pour effectuer un lancer
async function rollPins(pins) {
    const messageDiv = document.getElementById('message');
    messageDiv.className = 'message';
    
    try {
        const response = await fetch('/bowling/roll?pins=' + pins, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        });
        
        if (!response.ok) {
            throw new Error('Erreur réseau: ' + response.status);
        }
        
        const result = await response.json();
        
        // Afficher le message de résultat
        if (result.success) {
            messageDiv.textContent = `Lancer réussi ! Score: ${result.score}`;
            messageDiv.classList.add('success');
            // Recharger la page pour mettre à jour l'affichage
            setTimeout(() => window.location.reload(), 500);
        } else {
            messageDiv.textContent = 'Erreur: ' + (result.error || 'Erreur inconnue');
            messageDiv.classList.add('error');
        }
    } catch (error) {
        console.error('Erreur:', error);
        messageDiv.textContent = 'Erreur lors de la communication avec le serveur: ' + error.message;
        messageDiv.classList.add('error');
    } finally {
        messageDiv.classList.add('show');
    }
}

// Générer les boutons de lancer de quilles
function initializePinButtons() {
    const container = document.getElementById('pinsButtons');
    if (!container) return;
    
    for (let i = 0; i <= 10; i++) {
        const button = document.createElement('button');
        button.type = 'button';
        button.className = 'btn btn-outline-primary btn-pin';
        button.textContent = i === 10 ? 'Strike (X)' : i;
        button.onclick = () => rollPins(i);
        container.appendChild(button);
    }
}

// Initialisation du jeu
document.addEventListener('DOMContentLoaded', function() {
    initializePinButtons();
    
    // Ajout d'une animation aux boutons au chargement
    const buttons = document.querySelectorAll('.btn-pin');
    buttons.forEach((btn, index) => {
        btn.style.animation = `fadeIn 0.3s ease-out ${index * 0.05}s forwards`;
        btn.style.opacity = '0';
    });
});
