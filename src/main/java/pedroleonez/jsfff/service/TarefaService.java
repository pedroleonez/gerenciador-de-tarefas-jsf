package pedroleonez.jsfff.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pedroleonez.jsfff.model.Tarefa;
import pedroleonez.jsfff.repository.TarefaRepository;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class TarefaService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private TarefaRepository repository;

    @Inject
    private EntityManager em;

    public void salvar(Tarefa tarefa) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repository.salvar(tarefa);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Tarefa> listarTodas() {
        return repository.buscarTodas();
    }

    public void remover(Tarefa tarefa) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repository.excluir(tarefa.getId());
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void concluir(Tarefa tarefa) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tarefa t = repository.buscarPorId(tarefa.getId());
            if (t != null) {
                t.setConcluida(true);
                repository.salvar(t);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}