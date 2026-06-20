import axios from "axios";
import { useQuery } from "@tanstack/react-query";
import type { UsuarioDados } from "../interfaces/UsuarioDados";

const API_URL = "http://localhost:8080";

const buscarUsuarios = async (): Promise<UsuarioDados[]> => {
    const response = await axios.get<UsuarioDados[]>(`${API_URL}/usuarios`);
    return response.data;
};

export function useUsuarioDados() {
    return useQuery({
        queryKey: ["usuario-dados"],
        queryFn: buscarUsuarios,
        retry: 2,
    });
}
