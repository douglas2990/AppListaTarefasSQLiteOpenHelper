package com.douglas2990.aulas.applistatarefas

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglas2990.aulas.applistatarefas.adapter.TarefaAdapter
import com.douglas2990.aulas.applistatarefas.database.TarefaDAO
import com.douglas2990.aulas.applistatarefas.databinding.FragmentFirstBinding
import com.douglas2990.aulas.applistatarefas.model.Tarefa

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private var listaTarefas =  emptyList<Tarefa>()

    private var tarefaAdapter: TarefaAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdicionar.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_adicionarTarefaFragment)
        }

        //RecyclerView
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) }
        )
        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(context)


    }

    private fun editar(tarefa: Tarefa) {
        findNavController().navigate(R.id.action_FirstFragment_to_adicionarTarefaFragment, bundleOf("tarefa" to tarefa))
    }

    private fun confirmarExclusao(id: Int) {

        val alertBuilder = AlertDialog.Builder(context)

        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim"){ _, _ ->

            val tarefaDAO = TarefaDAO(requireContext().applicationContext)
            if ( tarefaDAO.remover( id ) ){
                atualizarListaTarefas()
                Toast.makeText(
                    context,
                    "Sucesso ao remover tarefa",
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(
                    context,
                    "Erro ao remover tarefa",
                    Toast.LENGTH_SHORT).show()
            }
        }

        alertBuilder.setNegativeButton("Não"){ _, _ -> }

        alertBuilder.create().show()

    }

    private fun atualizarListaTarefas(){

        val tarefaDAO = TarefaDAO(requireContext().applicationContext)
        ////abaixo
        listaTarefas = tarefaDAO.listar()
        ///acima
        tarefaAdapter?.adicionarLista( listaTarefas )

    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}