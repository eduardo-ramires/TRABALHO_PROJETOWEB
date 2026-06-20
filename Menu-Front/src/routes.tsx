import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import PainelAdm from "./pages/PainelAdm";
import MenuUsuario from "./pages/MenuUsuario";
import NotFound from "./pages/NotFound";

const AppRoutes: React.FC = () => {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/adm" element={<PainelAdm />} />
            <Route path="/mesa/:mesa" element={<MenuUsuario />} />
            <Route path="*" element={<NotFound />} />
        </Routes>
    );
};

export default AppRoutes;
