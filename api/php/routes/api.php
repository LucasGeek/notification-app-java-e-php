<?php

use App\Http\Controllers\NotificationTypeController;
use App\Http\Middleware\ApiAuthenticateMiddleware;
use App\Http\Controllers\NotificationController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;

// Rotas públicas
Route::post('/register', [UserController::class, 'registerUser']);
Route::post('/login', [UserController::class, 'loginUser']);

Route::middleware(ApiAuthenticateMiddleware::class)->group(function () {
    // Rotas para UserController
    Route::get('/me', [UserController::class, 'getUserProfile']);

    // Rotas para NotificationController
    Route::prefix('news')->group(function () {
        // Criar uma nova notificação
        Route::post('/create', [NotificationController::class, 'createNotification']);

        // Listar todas as notificações do usuário autenticado
        Route::get('/me', [NotificationController::class, 'getNotificationsByUser']);

        // Listar notificações por tipo
        Route::get('/type/{typeId}', [NotificationController::class, 'getNotificationsByUserAndType']);

        // Atualizar uma notificação do usuário
        Route::put('/update/{newsId}', [NotificationController::class, 'updateNotification']);

        // Deletar uma notificação do usuário
        Route::delete('/delete/{newsId}', [NotificationController::class, 'deleteNotification']);
    });

    // Rotas para NotificationTypeController
    Route::prefix('type')->group(function () {
        // Criar um novo tipo de notificação
        Route::post('/create', [NotificationTypeController::class, 'createNotificationType']);

        // Listar todos os tipos de notificação do usuário autenticado
        Route::get('/me', [NotificationTypeController::class, 'getNotificationTypesByUser']);

        // Atualizar um tipo de notificação
        Route::put('/update/{typeId}', [NotificationTypeController::class, 'updateNotificationType']);

        // Excluir um tipo de notificação
        Route::delete('/delete/{typeId}', [NotificationTypeController::class, 'deleteNotificationType']);
    });
});
