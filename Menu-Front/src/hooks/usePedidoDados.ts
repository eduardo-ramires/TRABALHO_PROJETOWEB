import axios from "axios";
import { useQuery } from "@tanstack/react-query";
import type { PedidoListado } from "../interfaces/PedidoListado";

const API_URL = "http://localhost:8080";

const buscarDados = async (): Promise<PedidoListado[]> => {
    const response = await axios.get<PedidoListado[]>(`${API_URL}/pedidos`);
    return response.data;
};

export function usePedidoDados() {
    return useQuery({
        queryKey: ["pedido-dados"],
        queryFn: buscarDados,
        retry: 2,
        refetchInterval: 5000,
        refetchIntervalInBackground: true,
    });
}