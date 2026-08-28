import { render, screen, fireEvent } from '@testing-library/react';
import { CartaoProduto } from './CartaoProduto';
import type { ProdutoDados } from '../interfaces/ProdutoDados';

const mockDeletar = vi.hoisted(() => vi.fn());
const mockEditar = vi.hoisted(() => vi.fn());

vi.mock('../hooks/useProdutoDadosDelete', () => ({
    useProdutoDadosDelete: () => ({ mutate: mockDeletar }),
}));

vi.mock('../hooks/useProdutoDadosEdit', () => ({
    useProdutoDadosEdit: () => ({ mutate: mockEditar }),
}));

const produtoMock: ProdutoDados = {
    id: 1,
    nome: 'Pizza Margherita',
    descricao: 'Pizza com molho de tomate e mussarela',
    preco: 35.90,
    categoria: 'Pizza',
    disponibilidade: true,
    imagem: 'pizza.jpg',
};

describe('CartaoProduto', () => {
    const onEditar = vi.fn();
    const onVisualizar = vi.fn();

    beforeEach(() => {
        vi.clearAllMocks();
    });

    describe('renderização', () => {
        it('exibe o nome do produto', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByRole('heading', { name: 'Pizza Margherita' })).toBeInTheDocument();
        });

        it('exibe a descrição do produto', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByText('Pizza com molho de tomate e mussarela')).toBeInTheDocument();
        });

        it('exibe a categoria do produto', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            const label = screen.getByText('Categoria:');
            expect(label.closest('p')?.textContent).toContain('Pizza');
        });

        it('exibe o preço formatado com duas casas decimais', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByText(/35\.90/)).toBeInTheDocument();
        });

        it('exibe a imagem do produto com alt correto', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByAltText('Imagem de Pizza Margherita')).toBeInTheDocument();
        });
    });

    describe('disponibilidade', () => {
        it('exibe "Disponível" quando disponibilidade é true', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByText('Disponível')).toBeInTheDocument();
        });

        it('exibe "Indisponível" quando disponibilidade é false', () => {
            render(<CartaoProduto produto={{ ...produtoMock, disponibilidade: false }} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByText('Indisponível')).toBeInTheDocument();
        });

        it('checkbox está marcado quando produto está disponível', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByRole('checkbox')).toBeChecked();
        });

        it('checkbox não está marcado quando produto está indisponível', () => {
            render(<CartaoProduto produto={{ ...produtoMock, disponibilidade: false }} onEditar={onEditar} onVisualizar={onVisualizar} />);
            expect(screen.getByRole('checkbox')).not.toBeChecked();
        });

        it('ao clicar no checkbox, chama editar com disponibilidade invertida (true → false)', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByRole('checkbox'));
            expect(mockEditar).toHaveBeenCalledWith({ ...produtoMock, disponibilidade: false });
        });

        it('ao clicar no checkbox, chama editar com disponibilidade invertida (false → true)', () => {
            const produtoIndisponivel = { ...produtoMock, disponibilidade: false };
            render(<CartaoProduto produto={produtoIndisponivel} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByRole('checkbox'));
            expect(mockEditar).toHaveBeenCalledWith({ ...produtoIndisponivel, disponibilidade: true });
        });
    });

    describe('botões de ação', () => {
        it('chama onVisualizar com o produto ao clicar em Ver', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByText('Ver'));
            expect(onVisualizar).toHaveBeenCalledWith(produtoMock);
        });

        it('chama onEditar com o produto ao clicar em Editar', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByText('Editar'));
            expect(onEditar).toHaveBeenCalledWith(produtoMock);
        });

        it('chama deletar com o id do produto ao clicar em Excluir', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByText('Excluir'));
            expect(mockDeletar).toHaveBeenCalledWith(1);
        });

        it('onEditar é chamado apenas uma vez ao clicar em Editar', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByText('Editar'));
            expect(onEditar).toHaveBeenCalledTimes(1);
        });

        it('onVisualizar é chamado apenas uma vez ao clicar em Ver', () => {
            render(<CartaoProduto produto={produtoMock} onEditar={onEditar} onVisualizar={onVisualizar} />);
            fireEvent.click(screen.getByText('Ver'));
            expect(onVisualizar).toHaveBeenCalledTimes(1);
        });
    });
});
