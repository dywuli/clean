package com.android.saleplatform

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.arch.data.room.entity.AddressEntity
import com.android.arch.data.room.entity.GoodsEntity
import com.android.arch.data.room.entity.UserEntity
import com.android.saleplatform.databinding.ActivityMainBinding
import com.android.saleplatform.internal.di.HasComponent
import com.android.saleplatform.internal.di.components.DaggerUserComponent
import com.android.saleplatform.internal.di.components.UserComponent
import com.android.saleplatform.ui.activity.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity(), HasComponent<UserComponent> {
    private var userComponent: UserComponent? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_info
//                R.id.navigation_home, R.id.navigation_orders, R.id.navigation_info
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//
//        var goodId =
//            applicationComponent.goodsDaoRepository()?.insertGoods(GoodsEntity(0, "xx", "00"))
//        var addId = applicationComponent.addressDaoRepository()
//            ?.insertAddress(AddressEntity(0, goodId!!, addressName = "999"))
//        var uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "777", "9-1010", 9, 1, 0, 0))
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "777", "9-1010", 9, 1, 0, 0))
//
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "888", "9-1010", 9, 1, 1, 1))
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "888", "9-1010", 9, 1, 1, 1))
//
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "999", "9-1010", 9, 0, 1, 0))
//
//        Log.e("wuli", "((((($goodId((((($addId(((((((($uid(((")
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "999", "9-1010", 9, 0, 1, 0))
//        Log.e("wuli", "((((($goodId((((($addId(((((((($uid(((")
//
//        uid = applicationComponent.userDaoRepository()
//            ?.insertUser(UserEntity(0, addId!!, "999", "9-1010", 9, 0, 0, 0))
//        Log.e("wuli", "((((($goodId((((($addId(((((((($uid(((")
////        instance?.getUserDao()?.getUserDetailByTakeStatus(1)?.subscribeOn(Schedulers.io())
//            ?.observeOn(Schedulers.io())?.subscribe {it ->
//                Log.e("wuli", it.toString())
//
//            }

    }

    private fun initializeInjector() {
        this.userComponent =
            DaggerUserComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(activityModule)
                .build()
    }

    override fun getComponent(): UserComponent? {
        return userComponent
    }
}