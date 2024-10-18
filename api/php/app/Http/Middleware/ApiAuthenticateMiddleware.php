<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;

class ApiAuthenticateMiddleware
{
    public function handle($request, Closure $next): Response
    {
        if (!Auth::guard('api')->check()) {
            return response()->json([
                'status' => 'error',
                'message' => 'Não autorizado. Forneça um token válido.',
            ], 401);
        }

        return $next($request);
    }
}
