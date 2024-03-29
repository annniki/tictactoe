var gameModule = angular.module('gameModule', []);

gameModule.controller('newGameController', ['$rootScope', '$scope', '$http', '$location',

    function (rootScope, scope, http, location) {

        rootScope.gameId = null;
        scope.newGameData = null;

        scope.newGameOptions = {
            availablePieces: [
                {name: 'X'},
                {name: 'O'}
            ],
            selectedPiece: {name: 'O'}
        };

        scope.createNewGame = function () {

            var data = scope.newGameData;
            var params = JSON.stringify(data);

            http.post("/game/create", params, {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data, status, headers, config) {
                rootScope.gameId = data.id;
                location.path('/game/' + rootScope.gameId);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }
]);


gameModule.controller('gamesToJoinController', ['$scope', '$http', '$location',
    function (scope, http, location) {

        scope.gamesToJoin = [];

        http.get('/game/list').success(function (data) {
            scope.gamesToJoin = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });


        function RefreshListGames() {

            http.get('/game/list').success(function (data) {
                scope.gamesToJoin = data;
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');

            });
        }

        setInterval(RefreshListGames, 3000);


        scope.joinGame = function (id) {

            var params = {"id": id}

            http.post('/game/join', params, {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data) {
                location.path('/game/' + data.id);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }]);


gameModule.controller('playerGamesController', ['$scope', '$http', '$location', '$routeParams',
    function (scope, http, location, routeParams) {

        scope.playerGames = [];

        http.get('/game/player/list').success(function (data) {
            scope.playerGames = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });

        scope.loadGame = function (id) {
            console.log(id);
            location.path('/game/' + id);
        }

    }]);


gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',
    function (rootScope, routeParams, scope, http) {
        var gameStatus;
        getInitialData()

        function getInitialData() {

            http.get('/game/' + routeParams.id).success(function (data) {
                scope.gameProperties = data;
                gameStatus = scope.gameProperties.gameStatus;
                getStepHistory();
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
        }

        function getStepHistory() {
            scope.stepsInGame = [];

            return http.get('/step/list').success(function (data) {
                scope.stepsInGame = data;
                scope.playerSteps = [];

                //paint the board with positions from the retrieved steps
                angular.forEach(scope.stepsInGame, function (step) {
                    scope.rows[step.boardRow - 1][step.boardColumn - 1].letter = step.playerPieceCode;
                });
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed to load steps in game"
            });
        }

        function checkPlayerTurn() {
            return http.get('/step/turn').success(function (data) {
                scope.playerTurn = data;
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed to get the player turn"
            });
        }

        function getNextStep() {

            scope.nextStepData = []
            console.log(' another player step');
        }

        function checkIfBoardCellAvailable(boardRow, boardColumn) {

            for (var i = 0; i < scope.stepsInGame.length; i++) {
                var step = scope.stepsInGame[i];
                if (step.boardColumn == boardColumn && step.boardRow == boardRow) {
                    return false;
                }
            }
            return true;
        }

        scope.rows = [
            [
                {'id': '11', 'letter': '', 'class': 'box'},
                {'id': '12', 'letter': '', 'class': 'box'},
                {'id': '13', 'letter': '', 'class': 'box'}
            ],
            [
                {'id': '21', 'letter': '', 'class': 'box'},
                {'id': '22', 'letter': '', 'class': 'box'},
                {'id': '23', 'letter': '', 'class': 'box'}
            ],
            [
                {'id': '31', 'letter': '', 'class': 'box'},
                {'id': '32', 'letter': '', 'class': 'box'},
                {'id': '33', 'letter': '', 'class': 'box'}
            ]
        ];

        angular.forEach(scope.rows, function (row) {
            row[0].letter = row[1].letter = row[2].letter = '';
            row[0].class = row[1].class = row[2].class = 'box';
        });


        scope.markPlayerStep = function (column) {
            checkPlayerTurn().success(function () {

                var boardRow = parseInt(column.id.charAt(0));
                var boardColumn = parseInt(column.id.charAt(1));
                var params = {'boardRow': boardRow, 'boardColumn': boardColumn}

                if (checkIfBoardCellAvailable(boardRow, boardColumn) == true) {
                    // if player has a turn
                    if (scope.playerTurn == true) {

                        http.post("/step/create", params, {
                            headers: {
                                'Content-Type': 'application/json; charset=UTF-8'
                            }
                        }).success(function () {
                            getStepHistory().success(function () {

                                var gameStatus = scope.stepsInGame[scope.stepsInGame.length - 1].gameStatus;
                                if (gameStatus == 'IN_PROGRESS') {
                                    getNextStep();
                                } else {
                                    alert(gameStatus)
                                }

                            });

                        }).error(function (data, status, headers, config) {
                            scope.errorMessage = "Can't send the step"
                        });

                    }
                }
            });
        };


        function RefreshBoard() {

            console.info(scope.playerTurn);
            console.info(scope.stepsInGame.length);


            http.get('/step/turn')
                .success(function (data) {
                    scope.playerTurn = data;
                })
                .error(function () {
                    scope.errorMessage = "Failed"
                })

            if (!scope.playerTurn) {
                http.get('/step/list')
                    .success(function (data) {
                        console.info(data.length);
                        if (data.length != scope.stepsInGame.length) {
                            getInitialData();
                        }
                    })
                    .error(function () {
                        scope.errorMessage = "Failed"
                    })


            }


        };

        setInterval(RefreshBoard, 3000);

    }
]);



