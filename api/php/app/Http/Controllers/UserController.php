<?php

namespace App\Http\Controllers;

use App\Http\Requests\UserRegisterRequest;
use App\Http\Requests\UserLoginRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use App\Services\UserService;

/**
 * @OA\Tag(
 *     name="Usuários",
 *     description="APIs para gerenciamento de usuários"
 * )
 */
class UserController extends Controller
{
    private UserService $userService;

    public function __construct(UserService $userService)
    {
        $this->userService = $userService;
    }

    /**
     * @OA\Post(
     *     path="/api/register",
     *     tags={"Usuários"},
     *     summary="Registrar um novo usuário",
     *     description="Cria um novo usuário com os dados fornecidos.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/UserRegisterRequest")
     *     ),
     *     @OA\Response(response=201, description="Usuário registrado com sucesso"),
     *     @OA\Response(response=400, description="Dados inválidos"),
     *     security={}
     * )
     */
    public function registerUser(UserRegisterRequest $request)
    {
        if ($this->userService->findByEmail($request->email)) {
            return response()->json([
                'status' => 'error',
                'message' => 'E-mail já cadastrado',
            ], 400);
        }

        $user = $this->userService->create([
            'nome' => $request->nome,
            'sobrenome' => $request->sobrenome,
            'email' => $request->email,
            'password' => Hash::make($request->password),
        ]);

        return response()->json($user, 201);
    }

    /**
     * @OA\Post(
     *     path="/api/login",
     *     tags={"Usuários"},
     *     summary="Login do usuário",
     *     description="Autentica o usuário e retorna um token JWT.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/UserLoginRequest")
     *     ),
     *     @OA\Response(response=200, description="Login realizado com sucesso"),
     *     @OA\Response(response=401, description="Credenciais inválidas"),
     *     security={}
     * )
     */
    public function loginUser(UserLoginRequest $request)
    {
        $credentials = ['email' => $request->email, 'password' => $request->password];

        $token = Auth::attempt($credentials);
        if (!$token) {
            return response()->json([
                'status' => 'error',
                'message' => 'Não autorizado',
            ], 401);
        }

        return response()->json($token);
    }

    /**
     * @OA\Get(
     *     path="/api/me",
     *     tags={"Usuários"},
     *     summary="Obter dados do usuário autenticado",
     *     description="Retorna os dados do usuário autenticado.",
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Dados do usuário autenticado"),
     *     @OA\Response(response=401, description="Usuário não autenticado")
     * )
     */
    public function getUserProfile()
    {
        return response()->json(Auth::user());
    }
}
