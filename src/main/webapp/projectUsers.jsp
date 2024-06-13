<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Users Management</title>
    <link rel="stylesheet" type="text/css" href="resources/css/projectUsers.css">
</head>
<body>
<h1>Project Users Management</h1>

<div class="form-container">
    <h2>Project Users List</h2>
    <table>
        <tr>
            <th>Project ID</th>
            <th>User ID</th>
            <th>Action</th>
        </tr>
        <c:forEach var="projectUser" items="${projectUsers}">
            <tr>
                <td>${projectUser.projectId}</td>
                <td>${projectUser.userId}</td>
                <td>
                    <!-- Delete Project User Form -->
                    <form action="ProjectUserServlet" method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="projectId" value="${projectUser.projectId}">
                        <input type="hidden" name="userId" value="${projectUser.userId}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="form-container">
    <h2>Assign User to Project</h2>
    <form action="ProjectUserServlet" method="post">
        <div>
            <input type="number" name="projectId" placeholder="Project ID" required>
        </div>
        <div>
            <input type="number" name="userId" placeholder="User ID" required>
        </div>
        <input type="submit" value="Assign">
    </form>
</div>

</body>
</html>
