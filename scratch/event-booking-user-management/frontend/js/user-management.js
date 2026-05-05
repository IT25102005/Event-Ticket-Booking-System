// js/user-management.js

document.addEventListener('DOMContentLoaded', () => {
    // Initial static sample data for demonstration before API integration
    const sampleUsers = [
        { id: 1, firstName: 'Akmal', lastName: 'Developer', email: 'akmal@example.com', role: 'ADMIN', status: 'ACTIVE' },
        { id: 2, firstName: 'Sarah', lastName: 'Connor', email: 'sarah@example.com', role: 'CUSTOMER', status: 'ACTIVE' },
        { id: 3, firstName: 'John', lastName: 'Doe', email: 'john.d@example.com', role: 'CUSTOMER', status: 'INACTIVE' },
        { id: 4, firstName: 'Jane', lastName: 'Smith', email: 'jane.s@example.com', role: 'CUSTOMER', status: 'SUSPENDED' }
    ];

    const tableBody = document.getElementById('userTableBody');
    const searchInput = document.getElementById('searchInput');
    const roleFilter = document.getElementById('roleFilter');
    const statusFilter = document.getElementById('statusFilter');

    // Function to render users into the table
    function renderUsers(users) {
        tableBody.innerHTML = '';
        
        if (users.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="5" style="text-align: center; padding: 2rem;">No users found.</td></tr>`;
            return;
        }

        users.forEach(user => {
            const tr = document.createElement('tr');
            
            // Get initials for avatar
            const initials = `${user.firstName.charAt(0)}${user.lastName.charAt(0)}`.toUpperCase();

            tr.innerHTML = `
                <td>
                    <div class="user-info">
                        <div class="avatar">${initials}</div>
                        <div class="user-details-text">
                            <span class="user-name">${user.firstName} ${user.lastName}</span>
                            <span class="user-email">${user.email}</span>
                        </div>
                    </div>
                </td>
                <td>
                    <span class="badge role-${user.role.toLowerCase()}">${user.role}</span>
                </td>
                <td>
                    <span class="badge status-${user.status.toLowerCase()}">${user.status}</span>
                </td>
                <td>
                    <div class="action-btns">
                        <button class="btn btn-view" onclick="viewUser(${user.id})">View</button>
                        <button class="btn btn-edit" onclick="editUser(${user.id})">Edit</button>
                        <button class="btn btn-delete" onclick="deleteUser(${user.id})">Delete</button>
                    </div>
                </td>
            `;
            tableBody.appendChild(tr);
        });
        
        updateSummaryCards(users);
    }

    // Function to update the summary numbers based on data
    function updateSummaryCards(users) {
        document.getElementById('totalUsers').innerText = users.length;
        document.getElementById('activeUsers').innerText = users.filter(u => u.status === 'ACTIVE').length;
        document.getElementById('adminUsers').innerText = users.filter(u => u.role === 'ADMIN').length;
        document.getElementById('customerUsers').innerText = users.filter(u => u.role === 'CUSTOMER').length;
    }

    // Filter functionality
    function filterData() {
        const query = searchInput.value.toLowerCase();
        const role = roleFilter.value;
        const status = statusFilter.value;

        const filtered = sampleUsers.filter(user => {
            const matchesSearch = user.firstName.toLowerCase().includes(query) || 
                                  user.lastName.toLowerCase().includes(query) || 
                                  user.email.toLowerCase().includes(query);
            const matchesRole = role === 'ALL' || user.role === role;
            const matchesStatus = status === 'ALL' || user.status === status;

            return matchesSearch && matchesRole && matchesStatus;
        });

        renderUsers(filtered);
    }

    // Event listeners for filters
    searchInput.addEventListener('input', filterData);
    roleFilter.addEventListener('change', filterData);
    statusFilter.addEventListener('change', filterData);

    // Initial render
    renderUsers(sampleUsers);

    // Future API integration placeholder
    /*
    async function fetchUsers() {
        try {
            const response = await fetch('/api/users');
            const data = await response.json();
            renderUsers(data);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    }
    // fetchUsers();
    */
});

// Global functions for action buttons
function viewUser(id) {
    console.log('Viewing user:', id);
    alert('View user details for ID: ' + id);
}

function editUser(id) {
    console.log('Editing user:', id);
    alert('Redirect to edit profile for ID: ' + id);
}

function deleteUser(id) {
    const confirmDelete = confirm('Are you sure you want to delete this user?');
    if (confirmDelete) {
        console.log('Deleted user:', id);
        alert('User deleted successfully!');
        // Ideally we would fetch users again here
    }
}
