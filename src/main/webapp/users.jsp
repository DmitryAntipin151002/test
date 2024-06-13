<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" type="text/css" href="resources/css/users.css">

</head>
<body>
<h1>User Management</h1>

<div class="form-container">
    <h2>Users List</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Password Hash</th>
            <th>Action</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.passwordHash}</td>
                <td>
                    <!-- Edit User Form -->
                    <form action="UserServlet" method="post">
                        <input type="hidden" name="_method" value="PUT">
                        <input type="hidden" name="id" value="${user.id}">
                        <div>
                            <input type="email" name="email" value="${user.email}" placeholder="Email" required>
                        </div>
                        <div>
                            <input type="password" name="passwordHash" value="${user.passwordHash}" placeholder="Password Hash" required>
                        </div>
                        <input type="submit" value="Edit">
                    </form>
                    <!-- Delete User Form -->
                    <form action="UserServlet" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="form-container">
    <h2>Create User</h2>
    <form action="UserServlet" method="post">
        <div>
            <input type="email" name="email" placeholder="Email" required>
        </div>
        <div>
            <input type="password" name="passwordHash" placeholder="Password Hash" required>
        </div>
        <input type="submit" value="Create">
    </form>
</div>

</body>
</html>
