package by.it.a_khmelev.project08.java.controller;

import by.it.a_khmelev.project08.java.beans.User;
import by.it.a_khmelev.project08.java.dao.Dao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CmdEditUsers implements Cmd {
    @Override
    public Action execute(HttpServletRequest req) throws Exception {
        Dao dao = Dao.getDao();
        if (Form.isPost(req)) {
            long id = Form.getLong(req, "id");
            String login = Form.getString(req, "login");
            String password = Form.getString(req, "password");
            String email = Form.getString(req, "email");
            long roles_id = Form.getLong(req, "roles_id");
            User user = new User(id, login, email, password, roles_id);
            User currentUser = Util.findUser(req);
            if (req.getParameter("update") != null) {
                dao.user.update(user);
                if (currentUser != null && currentUser.getId() == user.getId()) {
                    req.getSession().setAttribute("user", user);
                }
            } else if (req.getParameter("delete") != null) {
                dao.user.delete(user);
                if (currentUser != null && currentUser.getId() == user.getId())
                    return Action.LOGOUT;
            }
        }

        List<User> users = Dao.getDao().user.getAll();
        req.setAttribute("users", users);
        return Action.EDITUSERS;
    }
}
