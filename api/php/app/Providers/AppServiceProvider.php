<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use App\Repositories\NotificationRepository;
use App\Repositories\Eloquent\EloquentNotificationRepository;
use App\Repositories\NotificationTypeRepository;
use App\Repositories\Eloquent\EloquentNotificationTypeRepository;
use App\Repositories\UserRepository;
use App\Repositories\Eloquent\EloquentUserRepository;

class AppServiceProvider extends ServiceProvider
{
    public function register()
    {
        $this->app->bind(NotificationRepository::class, EloquentNotificationRepository::class);
        $this->app->bind(NotificationTypeRepository::class, EloquentNotificationTypeRepository::class);
        $this->app->bind(UserRepository::class, EloquentUserRepository::class);
    }

    public function boot()
    {
        //
    }
}
