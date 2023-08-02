package com.example.ktapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.ktapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private companion object

    val TAG = "FirstFragment1"
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var job: Job? = null
    private var mainScope: CoroutineScope? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_FirstFragment_to_SecondFragment)
//            job = globalScopeTest()
//            globalScopeTest()


            mainScope = MainScope()
//            job = mainScopeTest()
//            job?.cancel()
//            mainScopeTest2()
//
//
//            job?.invokeOnCompletion {
//                Log.d(TAG, "error::" + it)
//            }

            /////
            GlobalScope.launch(start = CoroutineStart.DEFAULT){

            }
            var scope = CoroutineScope(Job())
            scope.launch {
                val asyncA = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
                val asyncB = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
//                asyncA.await()
//                asyncB.await()

                val resultA = kotlin.runCatching { asyncA.await() }
                val resultB = kotlin.runCatching { asyncB.await() }
                withContext<Unit>(Dispatchers.Main){  }
                flow<Int>{}.map {  }
                    .flowOn(Dispatchers.IO)
                    .collect()
                var sharedFlow = MutableSharedFlow <Int>()
                sharedFlow.map {  }
                    .flowOn(Dispatchers.IO)

            }
            scope = CoroutineScope(SupervisorJob())
            //异常
//            scope.launch {
//                launch(CoroutineName("A") + SupervisorJob()) {
//                    delay(10)
//                    throw RuntimeException()
//                }
//                launch(CoroutineName("B")) {
//                    delay(200)
//                    Log.e(TAG, "猜猜我还能不能打印")
//                }
//            }

            //非异常
            scope.launch {
                var job1 =
                    launch(CoroutineName("A") + SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                        delay(10)
                        throw RuntimeException()
                    }
                job1.invokeOnCompletion {
                    Log.d(TAG, " ^^^^^^^^^^" + it)
                }
                launch(CoroutineName("B")) {
                    delay(200)
                    Log.e(TAG, "猜猜我还能不能打印")
                }
            }


//            scope = CoroutineScope(Job())
//            scope.launch {
//                val asyncA = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
//                    throw RuntimeException()
//                }
//                val asyncB = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
//                    throw RuntimeException()
//                }
//                asyncA.await()
//                asyncB.await()
//            }

            scope = CoroutineScope(Job() + CoroutineExceptionHandler { r, r2 ->
                Log.e(TAG, "********(((99999999)))*********$r = $r2")
            })
            var jj = scope.launch {
                val asyncA = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
                val asyncB = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
                asyncA.await()
                asyncB.await()
//                val resultA = kotlin.runCatching { asyncA.await() }
//                val resultB = kotlin.runCatching { asyncB.await() }
            }

            scope = CoroutineScope(Job())
            scope.launch {
                val asyncA = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
                val asyncB = async(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
//                asyncA.await()
//                asyncB.await()
                val resultA = kotlin.runCatching { asyncA.await() }
                val resultB = kotlin.runCatching { asyncB.await() }

                resultA.onFailure {
                    Log.e(TAG, "&&&555&&&" + it)
                }
                resultB.onFailure {
                    Log.e(TAG, "&&&366&&&" + it)
                }
            }


            scope = CoroutineScope(Job())
            // async 作为根协程
            val asyncA = scope.async { throw NullPointerException("6666666666666666666") }
            val asyncB = scope.async { }
            scope.launch {
                // 此时可以直接tryCatch
                kotlin.runCatching {
                    asyncA.await()
                    asyncB.await()
                }
            }
            scope = CoroutineScope(Job() + CoroutineExceptionHandler { r, r2 ->
                Log.e(TAG, "*****************$r = $r2")
            })

            scope.launch {
                // async 不作为根协程, CoroutineExceptionHandle不起作用，向上传递，cancel job

                val asyncA = async() {
                    throw RuntimeException()
                }
                val asyncB = async(CoroutineExceptionHandler { _, _ -> }) {
                    throw RuntimeException()
                }
//                asyncA.await()
//                asyncB.await()
                val resultA = kotlin.runCatching { asyncA.await() }
                val resultB = kotlin.runCatching { asyncB.await() }
                resultA.onFailure {
                    Log.e(TAG, "&&&&&&" + it)
                }
                resultB.onFailure {
                    Log.e(TAG, "&&&3&&&" + it)
                }
//                Log.d(TAG, "%%%%%" + resultA + resultB)
            }


            scope = CoroutineScope(CoroutineExceptionHandler { r, r2 ->
                Log.e(TAG, "********%%%%%%%%*********$r = $r2")
            })
            scope.launch() {
                supervisorScope {
                    // launch A ❎
                    launch(CoroutineName("A")) {
                        delay(10)
                        throw RuntimeException()
                    }

                    // launch B 👍
                    launch(CoroutineName("B")) {
                        delay(100)
                        Log.e("petterp", "正常执行,我不会收到影响")
                    }
                }
            }


            scope = CoroutineScope(CoroutineExceptionHandler { r, r2 ->
                Log.e(TAG, "********%%%%%%%%*********$r = $r2")
            })
            scope.launch() {
                supervisorScope {
                    // launch A ❎
                    launch(CoroutineName("A") + CoroutineExceptionHandler { r, r2 ->
                        Log.e(TAG, "********%%%%%%%%*********$r = $r2")
                    }) {
                        delay(10)
                        throw RuntimeException()
                    }

                    // launch B 👍
                    launch(CoroutineName("B")) {
                        delay(100)
                        Log.e("petterp", "正常执行,我不会收到影响")
                    }
                }
            }

            val sendChannel: SendChannel<Int> = GlobalScope.actor<Int> {
//                for (ele in channel)
//                    Log.e(TAG, "******(((****" + ele)
                while (true) {
                    Log.e(TAG, "******(((****" + receive())
                }

            }
            val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
                var i = 0
                while (true) {
                    delay(1000)
                    send(i)
                    Log.e(TAG, "****555**" + i)
//                    sendChannel.send(i)
                    i++
                }

            }
            GlobalScope.launch {
                while (true) {
                    Log.e(TAG, "******" + receiveChannel.receive())
                    break
                }
//                for (i in receiveChannel) {
//                    Log.e(TAG, "******" + i)
//                }
            }
            GlobalScope.launch {
                while (true) {
                    Log.e(TAG, "****44**" + receiveChannel.receive())
                    break
                }
//                for (i in receiveChannel) {
//                    Log.e(TAG, "******" + i)
//                }
            }

            

            //////
        }



        binding.buttonSecond.setOnClickListener {
//            job?.cancel()
            Navigation.findNavController(it).navigate(R.id.action_FirstFragment_to_secondFragment13)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mainScopeTest(): Job {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, throwable.message ?: "Unkown Error")
        }
        return mainScope!!.launch() {
            launch(exceptionHandler) {
                sunpendF1()
                sunpendF2()
                val job1 = async {
                    delay(1000)
                    Log.d(TAG, "suspend async")
                    "5"
                }
                Log.d(TAG, "suspend async${job1.await()}")
            }
        }
    }

    fun mainScopeTest2(): Job {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, throwable.message ?: "Unkown Error")
        }
        return mainScope!!.launch() {
            launch(exceptionHandler) {
                sunpendF1()
                sunpendF2()
                val job1 = async {
                    delay(1000)
                    Log.d(TAG, "suspend async")
                    "5"
                }
                Log.d(TAG, "suspend async${job1.await()}")
            }
        }
    }

    fun globalScopeTest(): Job {

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, throwable.message ?: "Unkown Error")
        }
        MainScope()
        return GlobalScope.launch(exceptionHandler) {
            sunpendF1()
            sunpendF2()
            val job1 = async {
                delay(1000)
                Log.d(TAG, "suspend async")
                "5"
            }
            Log.d(TAG, "suspend async${job1.await()}")
        }

    }

    private suspend fun sunpendF1(): Int {
        delay(1000)
        Log.d(TAG, "suspend fun 1")
//        throw Exception("************")
        return 2
    }

    private suspend fun sunpendF2(): Int {
        delay(3000)
        Log.d(TAG, "suspend fun 2")
        return 4
    }
}