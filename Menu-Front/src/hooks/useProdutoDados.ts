import axios from "axios";
import { useQuery } from "@tanstack/react-query";
import type { ProdutoDados } from "../interfaces/ProdutoDados";

const API_URL = "http://localhost:8080";

const buscarDados = async (): Promise<ProdutoDados[]> => {
    const response = await axios.get<ProdutoDados[]>(`${API_URL}/produtos`);
    return response.data;
};

export function useProdutoDados() {
    return useQuery({
        queryKey: ["produto-dados"],
        queryFn: buscarDados,
        retry: 2,
    });
}