// Gestion de la suppression d'un utilisateur
document.addEventListener('DOMContentLoaded', function() {
    // Délégation d'événement pour les boutons de suppression
    document.body.addEventListener('click', function(event) {
        if (event.target.classList.contains('delete-btn')) {
            event.preventDefault();
            const userId = event.target.dataset.userId;
            if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
                deleteUser(userId);
            }
        }
    });
});

// Fonction pour supprimer un utilisateur via une requête AJAX
function deleteUser(userId) {
    fetch(`/deleteUser/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erreur lors de la suppression');
        }
        return response.text();
    })
    .then(data => {
        // Supprimer visuellement l'élément de la liste
        const userItem = document.querySelector(`.user-list-item[data-user-id="${userId}"]`);
        if (userItem) {
            userItem.style.opacity = '0';
            userItem.style.transform = 'translateX(-20px)';
            setTimeout(() => {
                userItem.remove();
                // Afficher le message "Aucun utilisateur" si nécessaire
                const userList = document.querySelector('.user-list');
                if (userList && userList.children.length === 0) {
                    const noUsersDiv = document.createElement('div');
                    noUsersDiv.className = 'no-users';
                    noUsersDiv.textContent = 'Aucun utilisateur pour le moment';
                    userList.parentNode.insertBefore(noUsersDiv, userList.nextSibling);
                }
            }, 300);
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
        alert('Une erreur est survenue lors de la suppression');
    });
}
